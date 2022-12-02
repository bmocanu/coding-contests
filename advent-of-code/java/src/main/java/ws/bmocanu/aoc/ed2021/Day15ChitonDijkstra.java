package ws.bmocanu.aoc.ed2021;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day15ChitonDijkstra extends SolutionBase {

    static FlexStruct flex;

    public static void main(String[] args) {
        flex = FlexStruct.fromFile(filePath("day15"));
        flex.forAllPoints()
            .mapData().charAsDigitToValue();

        findPathTo(99, 99);
        Log.part1(flex.point(99, 99).pathCount);

        for (int x = 0; x < 500; x++) {
            for (int y = 0; y < 500; y++) {
                int xAdd = x / 100;
                int yAdd = y / 100;
                flex.point(x, y).value =
                    XUtils.cycleInt(
                        flex.point(x - xAdd * 100, y - yAdd * 100).value + xAdd + yAdd,
                        1, 9);
            }
        }

        findPathTo(499, 499);
        Log.part2(flex.point(499, 499).pathCount);
    }

    public static void findPathTo(int destX, int destY) {
        flex.forAllPoints().setPathCount(Integer.MAX_VALUE).unvisit();
        Point p00 = flex.point(0, 0);
        p00.pathCount = 0;
        List<Point> toVisit = new LinkedList<>(Arrays.asList(p00));

        Point pDest = flex.point(destX, destY);
        while (!pDest.visited) {
            int smallestPathCount = Integer.MAX_VALUE;
            Point currentPoint = null;
            for (Point p : toVisit) {
                if (p.pathCount < smallestPathCount) {
                    smallestPathCount = p.pathCount;
                    currentPoint = p;
                }
            }
            for (PosDelta4 delta : PosDelta4.deltaValues) {
                Point nn = flex.pointOrNull(currentPoint, delta);
                if (nn != null && !nn.visited) {
                    int tentativePathCount = currentPoint.pathCount + nn.value;
                    if (tentativePathCount < nn.pathCount) {
                        nn.pathCount = tentativePathCount;
                        toVisit.add(nn);
                    }
                }
            }
            currentPoint.visit();
            toVisit.remove(currentPoint);
        }
    }

}
