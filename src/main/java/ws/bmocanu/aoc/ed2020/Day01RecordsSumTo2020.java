package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.ListUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day01RecordsSumTo2020 extends SolutionBase {

    public static void main(String[] args) {
        List<Integer> intList = FileUtils.fileAsIntPerLineToIntList(filePath("day01"));

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
