package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day11SeatsGameOfLife extends SolutionBase {

    private static final int FREE = 1;
    private static final int TAKEN = 2;

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day11"));
        FlexStruct originalStruct = FlexStruct.fromLineList(stringLines);
        originalStruct.forAllPoints().mapData().charToType('L', FREE);

        FlexStruct struct = originalStruct.deepClone();
        boolean stillChanging = true;
        while (stillChanging) {
            FlexStruct newStruct = iterateForPart1(struct);
            stillChanging = !newStruct.typesToString().equals(struct.typesToString());
            struct = newStruct;
        }
        Log.part1(struct.countPointsOfType(TAKEN));

        struct = originalStruct.deepClone();
        stillChanging = true;
        while (stillChanging) {
            FlexStruct newStruct = iterateForPart2(struct);
            stillChanging = !newStruct.typesToString().equals(struct.typesToString());
            struct = newStruct;
        }
        Log.part2(struct.countPointsOfType(TAKEN));
    }

    public static FlexStruct iterateForPart1(FlexStruct struct) {
        FlexStruct newStruct = struct.deepClone();
        for (Point point : struct.allPoints()) {
            int occSeats = 0;
            if (point.type != 0) {
                for (PosDelta8 delta8 : PosDelta8.deltaValues) {
                    Point pointNear = struct.pointOrNull(point, delta8);
                    if (pointNear != null) {
                        if (pointNear.type == TAKEN) {
                            occSeats++;
                        }
                    }
                }
                if (point.type == FREE && occSeats == 0) {
                    newStruct.pointByPoint(point).type = TAKEN;
                } else if (point.type == TAKEN && occSeats >= 4) {
                    newStruct.pointByPoint(point).type = FREE;
                }
            }
        }
        return newStruct;
    }

    public static FlexStruct iterateForPart2(FlexStruct struct) {
        FlexStruct newStruct = struct.deepClone();
        for (Point point : struct.allPoints()) {
            int occSeats = 0;
            if (point.type != 0) {
                for (PosDelta8 delta8 : PosDelta8.deltaValues) {
                    Point pointNear = struct.pointOrNull(point, delta8);
                    while (pointNear != null && pointNear.type == 0) {
                        pointNear = struct.pointOrNull(pointNear, delta8);
                    }
                    if (pointNear != null) {
                        if (pointNear.type == TAKEN) {
                            occSeats++;
                        }
                    }
                }
                if (point.type == FREE && occSeats == 0) {
                    newStruct.pointByPoint(point).type = TAKEN;
                } else if (point.type == TAKEN && occSeats >= 5) {
                    newStruct.pointByPoint(point).type = FREE;
                }
            }
        }
        return newStruct;
    }

}
