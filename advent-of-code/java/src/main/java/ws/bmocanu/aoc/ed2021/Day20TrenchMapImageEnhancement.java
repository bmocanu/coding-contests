package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day20TrenchMapImageEnhancement extends SolutionBase {

    public static void main(String[] args) {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day20"));
        String enhLine = inputLines.get(0);
        inputLines.remove(0);
        inputLines.remove(0);

        int border = 100;
        FlexStruct flex = FlexStruct.fromLineListWithShift(inputLines, border, border);
        flex.forAllPoints().mapData()
                .charToValue('#', 1)
                .charToValue('.', 0);

        int[] enhArray = new int[enhLine.length()];
        for (int index = 0; index < enhLine.length(); index++) {
            enhArray[index] = (enhLine.charAt(index) == '#' ? 1 : 0);
        }

        int height = inputLines.size();
        int width = inputLines.get(0).length();

        for (int step = 0; step < 50; step++) {
            FlexStruct newFlex = new FlexStruct();
            for (int y = 0; y <= border * 2 + height; y++) {
                for (int x = 0; x <= border * 2 + width; x++) {
                    int[] nrBinary = new int[9];
                    for (int py = 0; py < 3; py++) {
                        for (int px = 0; px < 3; px++) {
                            Point nn = flex.pointOrNull(x - 1 + px, y - 1 + py);
                            nrBinary[py * 3 + px] = (nn == null ? step % 2 : nn.value);
                        }
                    }
                    int nr = (int) XNum.binaryToLong(nrBinary);
                    newFlex.point(x, y).value = enhArray[nr];
                }
            }
            flex = newFlex;

            if (step == 1) {
                Log.part1(flex.countPointsWithValue(1));
            }
            if (step == 49) {
                Log.part2(flex.countPointsWithValue(1));
            }
        }
    }

}
