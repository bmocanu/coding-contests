package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.ed2020.interp.InstructionType;
import ws.bmocanu.aoc.ed2020.interp.Interpreter;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day08GameConsoleCode extends SolutionBase {

    public static void main(String[] args) {
        List<String> stringList = FileUtils.fileAsStringPerLineToStringList(filePath("day08"));

        Interpreter interpreter = Interpreter.fromStringList(stringList);
        interpreter.run(true);
        Log.part1(interpreter.memoryAt(0));

        interpreter.instructions().forEach(inst -> {
            if (inst.type == InstructionType.JMP) {
                inst.type = InstructionType.NOP;
                interpreter.forAllInstructions().resetExecuted();
                interpreter.resetPointer().resetMemory();
                if (interpreter.run(true).finishedOk()) {
                    Log.part2(interpreter.memoryAt(0));
                }
                inst.type = InstructionType.JMP;
            } else if (inst.type == InstructionType.NOP) {
                inst.type = InstructionType.JMP;
                interpreter.forAllInstructions().resetExecuted();
                interpreter.resetPointer().resetMemory();
                if (interpreter.run(true).finishedOk()) {
                    Log.part2(interpreter.memoryAt(0));
                }
                inst.type = InstructionType.NOP;
            }
        });
    }

}
