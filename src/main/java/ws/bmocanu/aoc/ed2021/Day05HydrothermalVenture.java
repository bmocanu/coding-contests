package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.Cursor;
import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day05HydrothermalVenture extends SolutionBase {

    public static class Coord {
        int x1;
        int y1;
        int x2;
        int y2;
    }

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day05"));
        SBind bind = new SBind("(\\d+),(\\d+) -> (\\d+),(\\d+)", "x1", "y1", "x2", "y2");
        List<Coord> coordList = bind.bind(input, Coord.class);

        FlexStruct flex = new FlexStruct();

        for (Coord coord : coordList) {
            if (coord.x1 == coord.x2 || coord.y1 == coord.y2) {
                Cursor cursor = flex.cursor(coord.x1, coord.y1).createPointsAsNeeded();
                cursor.setTarget(coord.x2, coord.y2);
                do {
                    cursor.point.value++;
                    cursor.moveTowardsTarget();
                } while (cursor.isNotAtTarget());
                cursor.point.value++;
            }
        }
        Log.part1(flex.countPointsWhere(point -> point.value > 1));

        flex.forAllPoints().setValueTo(0);
        for (Coord coord : coordList) {
            Cursor cursor = flex.cursor(coord.x1, coord.y1).createPointsAsNeeded();
            cursor.setTarget(coord.x2, coord.y2);
            while (cursor.isNotAtTarget()) {
                cursor.point.value++;
                cursor.moveTowardsTarget();
            }
            cursor.point.value++;
        }
        Log.part2(flex.countPointsWhere(point -> point.value > 1));
    }

}
