package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.flex.Cursor;
import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.support.Log;

public class Day03 {

    static int typeSpace = 0;
    static int typeTree = 1;

    public static void main(String[] args) {
        FlexStruct struct = FlexStruct.fromFile("day03");
        struct.forAllPoints()
                .setTypeTo(typeSpace)
                .mapData()
                .charToType('#', typeTree);

        Cursor cursor = struct.cursor(0, 0).enableCycling(true, false);
        int trees = 0;
        while (cursor.y < struct.height()) {
            if (cursor.point.type == typeTree) {
                trees++;
            }
            cursor.moveBy(3, 1);
        }
        Log.part1(trees);

        int slopesX[] = new int[]{1, 3, 5, 7, 1};
        int slopesY[] = new int[]{1, 1, 1, 1, 2};
        long result = 1;
        for (int index = 0; index < 5; index++) {
            cursor = struct.cursor(0, 0).enableCycling(true, false);
            trees = 0;
            while (cursor.y < struct.height()) {
                if (cursor.point.type == typeTree) {
                    trees++;
                }
                cursor.moveBy(slopesX[index], slopesY[index]);
            }
            result *= trees;
        }
        Log.part2(result);
    }

}
