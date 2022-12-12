package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.Collection;

public class Day12HillClimbingAlgorithm extends SolutionBase {

    public static void main(String[] args) {
        FlexStruct flex = FlexStruct.fromFile(filePath("day12"));

        Point start = flex.firstPointByChar('S');
        start.chr = 'a';
        start.pathCount = 0;
        Point end = flex.firstPointByChar('E');
        end.chr = 'z';
        end.pathCount = 0;
        end.mark();

        boolean atLeastOnePointMarked = true;
        while (atLeastOnePointMarked) {
            atLeastOnePointMarked = false;
            for (Point current : flex.allPointsMarked()) {
                for (PosDelta4 delta : PosDelta4.deltaValues) {
                    Point point = flex.pointOrNull(current, delta);
                    if (point != null && !point.marked && (current.chr - point.chr <= 1)) {
                        point.mark();
                        point.pathCount = current.pathCount + 1;
                        atLeastOnePointMarked = true;
                    }
                }
            }
        }

        Log.part1(start.pathCount);

        Collection<Point> startPoints = flex.allPointsOfChar('a');
        int min = Integer.MAX_VALUE;
        for (Point p : startPoints) {
            if (p.pathCount > 0 && p.pathCount < min) {
                min = p.pathCount;
            }
        }

        Log.part2(min);
    }

}
