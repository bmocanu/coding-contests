package intcode

import "fmt"

type Processor struct {
	initialData  []int
	memory       []int64
	execPos      int64
	relativeBase int64
	inputBus     DataBus
	outputBus    DataBus
	Config       ProcessorConfig
}

type ProcessorConfig struct {
	Name               string
	PauseOnEmptyInput  bool
	PauseOnEachOutput  bool
	LogEachInstruction bool
	LogStatus          bool
	LogAll             bool
}

// ----------------------------------------------------------------------------------------------------

const (
	opCodeModePosition  = 0
	opCodeModeImmediate = 1
	opCodeModeRelative  = 2
)

const (
	opCodeAddition           = 1
	opCodeMultiplication     = 2
	opCodeInput              = 3
	opCodeOutput             = 4
	opCodeJumpIfNotZero      = 5
	opCodeJumpIfZero         = 6
	opCodeSetOneIfLessThan   = 7
	opCodeSetOneIfEqual      = 8
	opCodeAdjustRelativeBase = 9
	opCodeExit               = 99
)

// ----------------------------------------------------------------------------------------------------

func (proc *Processor) Init(memorySize int, initialData []int, inputBus DataBus, outputBus DataBus) *Processor {
	proc.memory = make([]int64, memorySize)
	for index := 0; index < len(initialData); index++ {
		proc.memory[index] = int64(initialData[index])
	}
	proc.initialData = initialData
	proc.execPos = 0
	proc.relativeBase = 0
	proc.inputBus = inputBus
	proc.outputBus = outputBus
	return proc
}

func (proc *Processor) ResetMemory() *Processor {
	for index := 0; index < len(proc.initialData); index++ {
		proc.memory[index] = int64(proc.initialData[index])
	}
	proc.execPos = 0
	proc.relativeBase = 0
	return proc
}

func (proc *Processor) Set(address int, value int64) *Processor {
	proc.memory[address] = value
	return proc
}

func (proc *Processor) Execute() bool {
	for proc.get(proc.execPos) != opCodeExit {
		var currentData = proc.get(proc.execPos)
		var op, code1, code2, code3 = proc.opCode(int(currentData))

		var mem1 = proc.get(proc.execPos + 1)
		var mem2 = proc.get(proc.execPos + 2)
		var mem3 = proc.get(proc.execPos + 3)

		var value1 = proc.value(code1, mem1)
		var value2 = proc.value(code2, mem2)

		proc.logStatus("Pos %d - processing %d - op=%d, code1=%d, code2=%d, code3=%d, m1=%d -> %d, m2=%d -> %d, m3=%d",
			proc.execPos, currentData, int64(op), int64(code1), int64(code2), int64(code3),
			mem1, value1, mem2, value2, mem3)

		switch op {
		case opCodeAddition:
			var address = proc.address(code3, mem3)
			proc.logInstruction("Adding two values: %d + %d => mem %d", value1, value2, address)
			proc.set(address, value1+value2)
			proc.jumpWith(4)
		case opCodeMultiplication:
			var address = proc.address(code3, mem3)
			proc.logInstruction("Multiplying two values: %d * %d => mem %d", value1, value2, address)
			proc.set(address, value1*value2)
			proc.jumpWith(4)
		case opCodeInput:
			var inputAvailable = proc.inputAvailable()
			if proc.Config.PauseOnEmptyInput && !inputAvailable {
				return false
			} else if !inputAvailable {
				fmt.Printf("Incode: ERROR: Input not available! Intcode will halt!\n")
				return true
			}
			proc.set(proc.address(code1, mem1), proc.receiveInput())
			proc.jumpWith(2)
		case opCodeOutput:
			proc.sendOutput(value1)
			proc.jumpWith(2)
			if proc.Config.PauseOnEachOutput {
				return false
			}
		case opCodeJumpIfNotZero:
			proc.logInstruction("Jump if not zero to address %d: %d", value2, value1)
			if value1 != 0 {
				proc.jumpTo(int(value2))
			} else {
				proc.jumpWith(3)
			}
		case opCodeJumpIfZero:
			proc.logInstruction("Jump if zero to address %d: %d", value2, value1)
			if value1 == 0 {
				proc.jumpTo(int(value2))
			} else {
				proc.jumpWith(3)
			}
		case opCodeSetOneIfLessThan:
			var address = proc.address(code3, mem3)
			proc.logInstruction("Set 1 to %d if lessThan, otherwise 0: %d ? %d", address, value1, value2)
			if value1 < value2 {
				proc.set(address, 1)
			} else {
				proc.set(address, 0)
			}
			proc.jumpWith(4)
		case opCodeSetOneIfEqual:
			var address = proc.address(code3, mem3)
			proc.logInstruction("Set 1 to %d if equal, otherwise 0: %d ? %d", address, value1, value2)
			if value1 == value2 {
				proc.set(address, 1)
			} else {
				proc.set(address, 0)
			}
			proc.jumpWith(4)
		case opCodeAdjustRelativeBase:
			proc.adjustRelativeBase(value1)
			proc.jumpWith(2)
		default:
			fmt.Printf("Invalid opCode found: [%d]\n", op)
		}
	}

	return true
}

// ----------------------------------------------------------------------------------------------------

func (proc *Processor) jumpWith(steps int) {
	proc.execPos += int64(steps)
	proc.logInstruction("Moving cursor with %d steps, to final pos %d", int64(steps), proc.execPos)
}

func (proc *Processor) jumpTo(position int) {
	proc.execPos = int64(position)
	proc.logInstruction("Moving cursor to pos %d", proc.execPos)
}

func (proc *Processor) adjustRelativeBase(delta int64) {
	proc.relativeBase += delta
	proc.logInstruction("Adjusting relative with delta %d, to final value %d", delta, proc.relativeBase)
}

func (proc *Processor) get(index int64) int64 {
	var value = proc.memory[index]
	proc.logInstruction("Getting value at pos %d: %d", index, value)
	return value
}

func (proc *Processor) set(index int64, value int64) {
	proc.memory[index] = value
	proc.logInstruction("Setting value at pos %d: %d", index, value)
}

func (proc *Processor) receiveInput() int64 {
	var value = proc.inputBus.Pull()
	proc.logInstruction("Getting input: %d", value)
	return value
}

func (proc *Processor) inputAvailable() bool {
	return !proc.inputBus.IsEmpty()
}

func (proc *Processor) sendOutput(value int64) {
	proc.outputBus.Push(value)
	proc.logInstruction("Sending output: %d", value)
}

func (proc *Processor) logInstruction(message string, values ...interface{}) {
	if proc.Config.LogEachInstruction || proc.Config.LogAll {
		fmt.Printf(proc.processorName()+": "+message+"\n", values...)
	}
}

func (proc *Processor) logStatus(message string, values ...interface{}) {
	if proc.Config.LogStatus || proc.Config.LogAll {
		fmt.Printf(proc.processorName()+": "+message+"\n", values...)
	}
}

func (proc *Processor) processorName() string {
	var name = proc.Config.Name
	if name == "" {
		name = "Intcode"
	}
	return name
}

func (proc *Processor) opCode(opValue int) (op int, code1 int, code2 int, code3 int) {
	op = opValue % 100
	opValue = opValue / 100
	code1 = opValue % 10
	opValue = opValue / 10
	code2 = opValue % 10
	opValue = opValue / 10
	code3 = opValue % 10
	return
}

func (proc *Processor) value(paramCode int, value int64) int64 {
	switch paramCode {
	case opCodeModePosition:
		return proc.get(value)
	case opCodeModeImmediate:
		return value
	case opCodeModeRelative:
		return proc.get(value + proc.relativeBase)
	default:
		fmt.Printf("Invalid param code: %d\n", paramCode)
		return 0
	}
}

func (proc *Processor) address(paramCode int, mem int64) int64 {
	switch paramCode {
	case opCodeModePosition:
		return mem
	case opCodeModeImmediate:
		fmt.Print("Invalid address calculation for code IMMEDIATE\n")
		return 0
	case opCodeModeRelative:
		return mem + proc.relativeBase
	default:
		fmt.Printf("Invalid param code: %d\n", paramCode)
		return 0
	}
}
