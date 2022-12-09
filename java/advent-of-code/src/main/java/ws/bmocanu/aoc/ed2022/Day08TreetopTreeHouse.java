package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day08TreetopTreeHouse extends SolutionBase {

    public static void main(String[] args) {
        FlexStruct flex = FlexStruct.fromFile(filePath("day08"));
        flex.forAllPoints().mapData().charAsDigitToValue();
        int visibleCount = 0;
        int maxScenicScore = Integer.MIN_VALUE;
        for (Point point : flex.allPoints()) {
            int scenicScore = 1;
            boolean visibleAlreadyIncremented = false;
            for (PosDelta4 delta : PosDelta4.deltaValues) {
                boolean visible = true;
                int treesVisible = 0;
                Point nextPoint = flex.pointOrNull(point, delta);
                while (nextPoint != null) {
                    treesVisible++;
                    if (nextPoint.value >= point.value) {
                        visible = false;
                        break;
                    }
                    nextPoint = flex.pointOrNull(nextPoint, delta);
                }
                scenicScore *= treesVisible;
                if (visible && !visibleAlreadyIncremented) {
                    visibleCount++;
                    visibleAlreadyIncremented = true;
                }
            }
            if (scenicScore > maxScenicScore) {
                maxScenicScore = scenicScore;
            }
        }
        Log.part1(visibleCount);
        Log.part2(maxScenicScore);
    }

}