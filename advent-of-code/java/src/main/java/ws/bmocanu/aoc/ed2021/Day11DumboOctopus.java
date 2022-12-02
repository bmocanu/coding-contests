package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day11DumboOctopus extends SolutionBase {

    static int flashes;

    public static void main(String[] args) {
        FlexStruct flex = FlexStruct.fromFile(filePath("day11"));
        flex.forAllPoints()
                .mapData()
                .charAsDigitToValue();

        for (int step = 1; /* until all octopuses flash */ true; step++) {
            for (Point point : flex.allPoints()) {
                if (!point.marked) {
                    if (point.value < 9) {
                        point.value++;
                    } else {
                        flashOctopus(point, flex);
                    }
                }
            }
            if (flex.countPointsWithValue(0) == flex.size()) {
                Log.part2(step);
                return;
            }
            flex.forAllPoints().unmark();
            if (step == 100) {
                Log.part1(flashes);
            }
        }
    }

    public static void flashOctopus(Point p, FlexStruct flex) {
        p.mark();
        p.value = 0;
        flashes++;
        for (PosDelta8 delta : PosDelta8.deltaValues) {
            Point n = flex.pointOrNull(p, delta);
            if (n != null && !n.marked) {
                if (n.value < 9) {
                    n.value++;
                } else {
                    flashOctopus(n, flex);
                }
            }
        }
    }

}
