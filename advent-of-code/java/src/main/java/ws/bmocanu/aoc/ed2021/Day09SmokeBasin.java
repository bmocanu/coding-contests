package ws.bmocanu.aoc.ed2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day09SmokeBasin extends SolutionBase {

    public static void main(String[] args) {
        final FlexStruct flex = FlexStruct.fromFile(filePath("day09"));
        flex.forAllPoints().mapData().charAsDigitToValue();
        final AtomicInteger sum = new AtomicInteger();
        final AtomicInteger basinMaxType = new AtomicInteger(1);
        flex.forEachPoint(point -> {
            boolean lowestPoint = true;
            for (PosDelta4 delta : PosDelta4.deltaValues) {
                Point n = flex.pointOrNull(point, delta);
                if (n != null) {
                    if (n.value <= point.value) {
                        lowestPoint = false;
                        break;
                    }
                }
            }
            if (lowestPoint) {
                sum.addAndGet(point.value + 1);
                point.type = basinMaxType.intValue();
                basinMaxType.incrementAndGet();
            }
        });
        Log.part1(sum.intValue());

        AtomicBoolean onePointMarked = new AtomicBoolean(false);
        do {
            onePointMarked.set(false);
            flex.forEachPointWithTypeDifferentThan(0, point -> {
                for (PosDelta4 delta : PosDelta4.deltaValues) {
                    Point n = flex.pointOrNull(point, delta);
                    if (n != null) {
                        if (n.value < 9 && n.type == 0) {
                            n.type = point.type;
                            onePointMarked.set(true);
                        }
                    }
                }
            });
        } while (onePointMarked.get());

        List<Basin> basins = new ArrayList<>();
        for (int currentType = 1; currentType < basinMaxType.intValue(); currentType++) {
            basins.add(new Basin(flex.countPointsOfType(currentType), currentType));
        }
        Collections.sort(basins);
        Log.part2(basins.get(0).size * basins.get(1).size * basins.get(2).size);
    }

    static class Basin implements Comparable<Basin> {
        int size;
        int type;

        public Basin(int size, int type) {
            this.size = size;
            this.type = type;
        }

        @Override
        public int compareTo(Basin o) {
            return -Integer.compare(size, o.size);
        }
    }

}
