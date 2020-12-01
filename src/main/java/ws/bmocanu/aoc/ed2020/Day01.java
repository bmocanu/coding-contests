package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.ListUtils;
import ws.bmocanu.aoc.utils.Log;

public class Day01 {

    public static void main(String[] args) {
        Log.appendToTimestampedFile("day01");
        List<Integer> intList = FileUtils.fileAsIntPerLineToIntList("day01.txt");

        ListUtils.iterateWith2IntCursors(intList, (nr1, nr2) -> {
            if (nr1 + nr2 == 2020) {
                Log.part1(nr1 * nr2);
            }
        });

        ListUtils.iterateWith3IntCursors(intList, (nr1, nr2, nr3) -> {
            if (nr1 + nr2 + nr3 == 2020) {
                Log.part2(nr1 * nr2 * nr3);
            }
        });
    }

}
