package ws.bmocanu.aoc.ed2020.interp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class CodeInterpreter {

    private List<CodeLine> codeLines;

    private int linePointer;

    private int[] memory = new int[10];

    // ----------------------------------------------------------------------------------------------------

    public static CodeInterpreter fromStringList(List<String> stringList) {
        CodeInterpreter interpreter = new CodeInterpreter();
        interpreter.loadFromStringList(stringList);
        return interpreter;
    }

    // ----------------------------------------------------------------------------------------------------

    public CodeInterpreter loadFromStringList(List<String> stringLines) {
        codeLines = new ArrayList<>();
        //SBinder binder = new SBinder("([a-z]+) ([0-9-+]+)", "command", "value");
        for (String line : stringLines) {
            //codeLines.add(binder.bind(line, Day08GameConsoleCode.Inst.class));
            int spaceIndex = line.indexOf(' ');
            CodeLine newCodeLine = new CodeLine();
            newCodeLine.instruction = InstructionType.getByValue(line.substring(0, spaceIndex));
            newCodeLine.value = Integer.parseInt(line.substring(spaceIndex + 1));
            codeLines.add(newCodeLine);
        }
        return this;
    }

    public CodeInterpreter resetLinePointer() {
        linePointer = 0;
        return this;
    }

    public CodeInterpreter resetMemory() {
        Arrays.fill(memory, 0);
        return this;
    }

    public int memoryAt(int index) {
        return memory[index];
    }

    public CodeLineActions forAllLines() {
        return new CodeLineActions();
    }

    public CodeState run(boolean toTheEnd) {
        CodeState status = new CodeState();
        while (linePointer < codeLines.size()) {
            CodeLine line = codeLines.get(linePointer);
            if (line.executed) {
                status.status = CodeStatusType.FINISHED_LOOP;
                return status;
            }
            line.executed = true;
            switch (line.instruction) {
                case NOP:
                    linePointer++;
                    break;
                case JMP:
                    linePointer += line.value;
                    break;
                case ACC:
                    memory[0] += line.value;
                    linePointer++;
                    break;
                default:
                    throw new IllegalStateException("Unknown operation type: " + line.instruction);
            }
            if (!toTheEnd) {
                status.status = CodeStatusType.RUNNING;
                return status;
            }
        }
        status.status = CodeStatusType.FINISHED_OK;
        return status;
    }

    // ----------------------------------------------------------------------------------------------------

    public class CodeLineActions {

        public CodeLineActions resetExecuted() {
            for (CodeLine line : codeLines) {
                line.executed = false;
            }
            return this;
        }

    }

}
