package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XList;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day01RecordsSumTo2020 extends SolutionBase {

    public static void main(String[] args) {
        List<Integer> intList = XRead.fileAsIntPerLineToIntList(filePath("day01"));

        XList.iterateWith2IntCursors(intList, (nr1, nr2) -> {
            if (nr1 + nr2 == 2020) {
                Log.part1(nr1 * nr2);
            }
        });

        XList.iterateWith3IntCursors(intList, (nr1, nr2, nr3) -> {
            if (nr1 + nr2 + nr3 == 2020) {
                Log.part2(nr1 * nr2 * nr3);
            }
        });
    }

}
