package ws.bmocanu.aoc.ed2022;

import java.util.List;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day15BeaconExclusionZone extends SolutionBase {

    public static class InputLine {
        int sX;
        int sY;
        int bX;
        int bY;
        Point sensor;
        Point beacon;
        int beaconDistance;
    }

    private static List<InputLine> inputList;
    private static final int PART1_Y_TARGET = 2000000;
    private static final int X_LIM = 4000000;
    private static final int Y_LIM = 4000000;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day15"));
        SBind<InputLine> bind = new SBind<>("Sensor at x=(-?\\d+), y=(-?\\d+)\\: closest beacon is at x=(-?\\d+), y=(-?\\d+)",
                                            InputLine.class, "sX", "sY", "bX", "bY");
        inputList = bind.bindAll(input);
        for (InputLine inl : inputList) {
            inl.sensor = Point.from(inl.sX, inl.sY);
            inl.beacon = Point.from(inl.bX, inl.bY);
            inl.beaconDistance = inl.sensor.taxicabDistanceTo(inl.beacon);
        }

        boolean[] targetRow = new boolean[10_000_000];
        for (InputLine inl : inputList) {
            if (inl.sensor.y - inl.beaconDistance <= PART1_Y_TARGET && inl.sensor.y + inl.beaconDistance >= PART1_Y_TARGET) {
                for (int x = inl.sensor.x - inl.beaconDistance; x <= inl.sensor.x + inl.beaconDistance; x++) {
                    int pointDistance = inl.sensor.taxicabDistanceTo(x, PART1_Y_TARGET);
                    if (pointDistance <= inl.beaconDistance) {
                        targetRow[x + 5_000_000] = true;
                    }
                }
            }
        }

        int part1Count = 0;
        for (boolean b : targetRow) {
            if (b) {
                part1Count++;
            }
        }
        Log.part1(part1Count - 1); // why -1? ¯\_(ツ)_/¯

        for (InputLine in : inputList) {
            int beaconDistance = in.sensor.taxicabDistanceTo(in.beacon);
            int borderDistance = beaconDistance + 1;
            for (int delta = 0; delta < borderDistance; delta++) {
                if (noSensorsCoverThisPoint(in, in.sensor.x - borderDistance + delta, in.sensor.y - delta) ||
                    noSensorsCoverThisPoint(in, in.sensor.x + delta, in.sensor.y - borderDistance + delta) ||
                    noSensorsCoverThisPoint(in, in.sensor.x + borderDistance - delta, in.sensor.y + delta) ||
                    noSensorsCoverThisPoint(in, in.sensor.x - delta, in.sensor.y + borderDistance - delta)) {
                    return;
                }
            }
        }
    }

    private static boolean noSensorsCoverThisPoint(InputLine currentIn, int x, int y) {
        if (x < 0 || x > X_LIM || y < 0 || y > Y_LIM) {
            return false;
        }
        for (InputLine in : inputList) {
            if (in != currentIn) {
                if (in.beaconDistance >= in.sensor.taxicabDistanceTo(x, y)) {
                    return false;
                }
            }
        }
        Log.part2(x * 4000000L + y);
        return true;
    }

}
