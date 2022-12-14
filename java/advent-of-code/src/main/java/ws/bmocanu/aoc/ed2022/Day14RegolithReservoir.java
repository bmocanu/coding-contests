package ws.bmocanu.aoc.ed2022;

import java.util.List;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day14RegolithReservoir extends SolutionBase {

    private static final int ROCK = 1;
    private static final int SAND = 2;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day14"));
        FlexStruct flex = new FlexStruct();

        for (String line : input) {
            String[] coordsList = line.split(" -> ");
            for (int coordIndex = 0; coordIndex < coordsList.length - 1; coordIndex++) {
                int x1 = Integer.parseInt(coordsList[coordIndex].split(",")[0]);
                int y1 = Integer.parseInt(coordsList[coordIndex].split(",")[1]);
                int x2 = Integer.parseInt(coordsList[coordIndex + 1].split(",")[0]);
                int y2 = Integer.parseInt(coordsList[coordIndex + 1].split(",")[1]);

                for (int x : XUtils.orderedArrayWithLimits(x1, x2)) {
                    for (int y : XUtils.orderedArrayWithLimits(y1, y2)) {
                        flex.point(x, y).type = ROCK;
                    }
                }
            }
        }

        Point sandSource = flex.point(500, 0);
        Log.part1(simulateSandFalling(flex.deepClone(), sandSource));

        int maxHeight = flex.height();
        for (int x = 0; x <= 1000; x++) {
            flex.point(x, maxHeight + 1).type = ROCK;
        }

        Log.part2(simulateSandFalling(flex, sandSource));
    }

    private static int simulateSandFalling(FlexStruct flex, Point sandSource) {
        int sandCount = 0;
        int maxHeight = flex.height();
        Point current = sandSource;
        while (current.y < maxHeight && sandSource.type == 0) {
            Point next = flex.point(current, PosDelta4.down());
            if (next.type != 0) {
                next = flex.point(current, PosDelta8.downLeft());
                if (next.type != 0) {
                    next = flex.point(current, PosDelta8.downRight());
                }
            }
            if (next.type != 0) {
                current.type = SAND;
                current = sandSource;
                sandCount++;
            } else {
                current = next;
            }
        }
        return sandCount;
    }

}
