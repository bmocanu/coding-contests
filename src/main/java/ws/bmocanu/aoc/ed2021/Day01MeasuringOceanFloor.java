package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day01MeasuringOceanFloor extends SolutionBase {

    public static void main(String[] args) {
        List<Integer> values = FileUtils.fileAsIntPerLineToIntList(filePath("day01"));
        int count = 0;
        for (int index = 0; index < values.size() - 1; index++) {
            if (values.get(index) < values.get(index + 1)) {
                count++;
            }
        }
        Log.part1(count);

        count = 0;
        for (int index = 0; index < values.size() - 3; index++) {
            if (values.get(index) + values.get(index + 1) + values.get(index + 2) <
                    values.get(index + 1) + values.get(index + 2) + values.get(index + 3)) {
                count++;
            }
        }
        Log.part2(count);
    }

}
