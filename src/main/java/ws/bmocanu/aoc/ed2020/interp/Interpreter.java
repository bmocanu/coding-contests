package ws.bmocanu.aoc.ed2020.interp;

import ws.bmocanu.aoc.support.Log;

import java.util.*;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Interpreter {

    private final Stack<Interpreter> snapshotStack = new Stack<>();

    private InterpreterConfig config = new InterpreterConfig();
    private List<Instruction> instructions = new ArrayList<>();
    private int instPointer;
    private int[] memory = new int[10];

    // ----------------------------------------------------------------------------------------------------

    public static Interpreter fromStringList(List<String> stringList) {
        Interpreter interpreter = new Interpreter();
        interpreter.loadFromStringList(stringList);
        return interpreter;
    }

    // ----------------------------------------------------------------------------------------------------

    public Interpreter loadFromStringList(List<String> stringLines) {
        instructions = new ArrayList<>();
        for (String line : stringLines) {
            addInstructionFromString(line);
        }
        return this;
    }

    public Interpreter loadFromInterpreter(Interpreter otherInt) {
        this.instPointer = otherInt.instPointer;
        this.memory = Arrays.copyOf(otherInt.memory, otherInt.memory.length);
        this.instructions.clear();
        otherInt.instructions.forEach(inst -> this.instructions.add(inst.deepClone()));
        return this;
    }

    public Interpreter addInstructionFromString(String str) {
        if (config.loadDataLog) {
            Log.info("Interpreter: adding line [%s]", str);
        }
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        Instruction newInstruction = new Instruction();
        InstructionType type = InstructionType.getByValue(tokenizer.nextToken());
        newInstruction.type = type;
        switch (type) {
            case NOP:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
                if (config.loadDataLog) {
                    Log.info("Interpreter: - NOP, value [%d]", newInstruction.value);
                }
                break;
            case JMP:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
                if (config.loadDataLog) {
                    Log.info("Interpreter: - JMP, value [%d]", newInstruction.value);
                }
                break;
            case ACC:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
                if (config.loadDataLog) {
                    Log.info("Interpreter: - JMP, value [%d]", newInstruction.value);
                }
                break;
            default:
                throw new IllegalStateException("Unknown instruction type: " + type);
        }
        instructions.add(newInstruction);
        return this;
    }

    public Interpreter resetPointer() {
        instPointer = 0;
        return this;
    }

    public Interpreter resetMemory() {
        Arrays.fill(memory, 0);
        return this;
    }

    public Instruction instructionAt(int index) {
        return instructions.get(index);
    }

    public List<Instruction> instructions() {
        return instructions;
    }

    public int memoryAt(int index) {
        return memory[index];
    }

    public InstructionActions forAllInstructions() {
        return new InstructionActions(instructions);
    }

    public InterpreterState run(boolean toTheEnd) {
        InterpreterState state = new InterpreterState();
        while (instPointer < instructions.size()) {
            Instruction line = instructions.get(instPointer);
            if (config.runLog) {
                Log.info("Interpreter: pointer index [%d], inst type [%s], inst value [%d], executed [%b], memory [%s]",
                        instPointer, line.type.name(), line.value, line.executed, Arrays.toString(memory));
            }
            if (line.executed) {
                if (config.runLog) {
                    Log.info("Interpreter: status FINISHED_LOOP");
                }
                state.status = StatusType.FINISHED_LOOP;
                return state;
            }
            line.executed = true;
            switch (line.type) {
                case NOP:
                    instPointer++;
                    break;
                case JMP:
                    instPointer += line.value;
                    break;
                case ACC:
                    memory[0] += line.value;
                    instPointer++;
                    break;
                default:
                    throw new IllegalStateException("Unknown instruction type: " + line.type);
            }
            if (!toTheEnd) {
                break;
            }
        }
        if (instPointer >= instructions.size()) {
            if (config.runLog) {
                Log.info("Interpreter: status FINISHED_OK");
            }
            state.status = StatusType.FINISHED_OK;
        } else {
            if (config.runLog) {
                Log.info("Interpreter: status RUNNING");
            }
            state.status = StatusType.RUNNING;
        }
        return state;
    }

    public Interpreter deepClone() {
        Interpreter newInterpreter = new Interpreter();
        newInterpreter.instPointer = instPointer;
        newInterpreter.memory = Arrays.copyOf(memory, memory.length);
        newInterpreter.instructions = new ArrayList<>();
        newInterpreter.config = config.deepClone();
        instructions.forEach(inst -> newInterpreter.instructions.add(inst.deepClone()));
        return newInterpreter;
    }

    public Interpreter pushSnapshot() {
        snapshotStack.push(deepClone());
        return this;
    }

    public Interpreter popSnapshot() {
        if (snapshotStack.isEmpty()) {
            throw new IllegalStateException("The snapshot stack is empty, nothing to pop");
        }
        loadFromInterpreter(snapshotStack.pop());
        return this;
    }

}
