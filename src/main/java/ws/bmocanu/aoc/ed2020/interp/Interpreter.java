package ws.bmocanu.aoc.ed2020.interp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Interpreter {

    //private final Stack<Interpreter> snapshotStack = new Stack<>();

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
            int spaceIndex = line.indexOf(' ');
            Instruction newInstruction = new Instruction();
            newInstruction.type = InstructionType.getByValue(line.substring(0, spaceIndex));
            newInstruction.value = Integer.parseInt(line.substring(spaceIndex + 1));
            instructions.add(newInstruction);
        }
        return this;
    }

    public Interpreter addInstructionFromString(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        Instruction newInstruction = new Instruction();
        InstructionType type = InstructionType.getByValue(tokenizer.nextToken());
        newInstruction.type = type;
        switch (type) {
            case NOP:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
                break;
            case JMP:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
                break;
            case ACC:
                newInstruction.value = Integer.parseInt(tokenizer.nextToken());
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
        return new InstructionActions();
    }

    public InterpreterState run(boolean toTheEnd) {
        InterpreterState state = new InterpreterState();
        while (instPointer < instructions.size()) {
            Instruction line = instructions.get(instPointer);
            if (line.executed) {
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
            state.status = StatusType.FINISHED_OK;
        } else {
            state.status = StatusType.RUNNING;
        }
        return state;
    }

    public Interpreter deepClone() {
        Interpreter newInterp = new Interpreter();
        newInterp.instPointer = instPointer;
        newInterp.memory = Arrays.copyOf(memory, memory.length);
        newInterp.instructions = new ArrayList<>();
        instructions.forEach(inst -> newInterp.instructions.add(inst.deepClone()));
        return newInterp;
    }

    // ----------------------------------------------------------------------------------------------------

    public class InstructionActions {

        public InstructionActions resetExecuted() {
            for (Instruction inst : instructions) {
                inst.executed = false;
            }
            return this;
        }

    }

}
