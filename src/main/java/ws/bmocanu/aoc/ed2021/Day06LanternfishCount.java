package ws.bmocanu.aoc.ed2021;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day06LanternfishCount extends SolutionBase {

    public static void main(String[] args) {
        List<Integer> input = XRead.fileAsCsvLineToIntList(filePath("day06"), ",");

        long[] fishNrPerDay = new long[9];
        for (Integer item : input) {
            fishNrPerDay[item]++;
        }

        for (int iter = 0; iter < 256; iter++) {
            long fishNr6 = 0;
            long fishNr8 = 0;
            for (int dayIndex = 0; dayIndex < fishNrPerDay.length; dayIndex++) {
                long ageCount = fishNrPerDay[dayIndex];
                if (dayIndex == 0) {
                    fishNr6 = ageCount;
                    fishNr8 = ageCount;
                } else {
                    fishNrPerDay[dayIndex - 1] = ageCount;
                }
            }

            fishNrPerDay[6] += fishNr6;
            fishNrPerDay[8] = fishNr8;

            if (iter == 79) {
                Log.part1(XNum.sumOfLongArray(fishNrPerDay));
            }
        }

        Log.part2(XNum.sumOfLongArray(fishNrPerDay));
    }

}
