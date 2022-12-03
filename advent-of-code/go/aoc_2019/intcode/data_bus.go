package intcode

import "fmt"

type DataBus interface {
	Push(value int64)
	Pull() int64
	Peek() int64
	IsEmpty() bool
	Reset()
	Size() int
}

// ----------------------------------------------------------------------------------------------------

type SimpleDataBus struct {
	firstCell *dataBusCell
	lastCell  *dataBusCell
	cellCount int
}

type dataBusCell struct {
	value    int64
	nextCell *dataBusCell
}

func (bus *SimpleDataBus) Push(value int64) {
	var newCell = new(dataBusCell)
	newCell.value = value
	if bus.firstCell == nil {
		bus.firstCell = newCell
	} else {
		bus.lastCell.nextCell = newCell
	}
	bus.lastCell = newCell
	bus.cellCount++
}

func (bus *SimpleDataBus) Pull() (value int64) {
	if bus.firstCell == nil {
		fmt.Printf("Error: DataBus is empty! Cannot Pull values from it\n")
		return 0
	}
	value = bus.firstCell.value
	bus.firstCell = bus.firstCell.nextCell
	if bus.firstCell == nil {
		bus.lastCell = nil
	}
	bus.cellCount--
	return
}

func (bus *SimpleDataBus) Peek() (value int64) {
	if bus.firstCell == nil {
		fmt.Printf("Error: DataBus is empty! Cannot Peek values from it\n")
		return 0
	}
	return bus.firstCell.value
}

func (bus *SimpleDataBus) IsEmpty() bool {
	return bus.firstCell == nil
}

func (bus *SimpleDataBus) Reset() {
	bus.firstCell = nil
	bus.lastCell = nil
	bus.cellCount = 0
}

func (bus *SimpleDataBus) Size() int {
	return bus.cellCount
}

// ----------------------------------------------------------------------------------------------------

type StdOutputBus struct {
}

func (bus *StdOutputBus) Push(value int64) {
	fmt.Printf("Intcode output: %d\n", value)
}

func (bus *StdOutputBus) Pull() (value int64) {
	fmt.Printf("Error: Pull is NOT defined for StdOutputBus\n")
	return 0
}

func (bus *StdOutputBus) Peek() (value int64) {
	fmt.Printf("Error: Peek is NOT defined for StdOutputBus\n")
	return 0
}

func (bus *StdOutputBus) Reset() {
	// no code here
}

func (bus *StdOutputBus) Size() int {
	fmt.Printf("Error: Size is NOT defined for StdOutputBus\n")
	return 0
}

func (bus *StdOutputBus) IsEmpty() bool {
	fmt.Printf("Error: IsEmpty is NOT defined for StdOutputBus\n")
	return true
}