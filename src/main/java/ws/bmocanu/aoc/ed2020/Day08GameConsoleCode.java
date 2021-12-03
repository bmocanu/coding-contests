package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.ed2020.interp.InstructionType;
import ws.bmocanu.aoc.ed2020.interp.Program;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day08GameConsoleCode extends SolutionBase {

    public static void main(String[] args) {
        List<String> stringList = XRead.fileAsStringPerLineToStringList(filePath("day08"));

        Program program = Program.fromStringList(stringList);
        program.run(true);
        Log.part1(program.memoryAt(0));

        program.instructions().forEach(inst -> {
            if (inst.type == InstructionType.JMP) {
                inst.type = InstructionType.NOP;
                program.forAllInstructions().resetExecuted();
                program.resetPointer().resetMemory();
                if (program.run(true).finishedOk()) {
                    Log.part2(program.memoryAt(0));
                }
                inst.type = InstructionType.JMP;
            } else if (inst.type == InstructionType.NOP) {
                inst.type = InstructionType.JMP;
                program.forAllInstructions().resetExecuted();
                program.resetPointer().resetMemory();
                if (program.run(true).finishedOk()) {
                    Log.part2(program.memoryAt(0));
                }
                inst.type = InstructionType.NOP;
            }
        });
    }

}
