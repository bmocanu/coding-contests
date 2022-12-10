package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day10CathodeRayTube extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day10"));

        int signalSum = 0;
        int registerX = 1;
        String currentInst = null;
        int currentInstCycles = 0;
        int cycle = 1;
        FlexStruct flex = new FlexStruct();

        for (int instIndex = -1; instIndex < input.size() - 1; cycle++) {
            if (currentInst == null) {
                instIndex++;
                currentInst = input.get(instIndex);
                currentInstCycles = currentInst.startsWith("noop") ? 1 : 2;
            }
            if (cycle == 20 || (cycle - 20) % 40 == 0) {
                signalSum += cycle * registerX;
            }

            int pixelDrawnOnLine = ((cycle - 1) % 40);
            int pixelDrawnX = (cycle - 1) % 40;
            int pixelDrawnY = (cycle - 1) / 40;

            if (registerX == pixelDrawnOnLine || registerX == pixelDrawnOnLine + 1 || registerX == pixelDrawnOnLine - 1) {
                flex.point(pixelDrawnX, pixelDrawnY).chr = '#';
            } else {
                flex.point(pixelDrawnX, pixelDrawnY).chr = '.';
            }

            currentInstCycles--;
            if (currentInstCycles == 0) {
                if (currentInst.startsWith("addx")) {
                    registerX += Integer.valueOf(currentInst.split(" ")[1]);
                }
                currentInst = null;
            }
        }

        Log.part1(signalSum);
        Log.part2("\n" + flex.charactersToString());
    }

}
