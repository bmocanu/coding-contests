package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day07CrabSubmarinesFuel extends SolutionBase {

    public static void main(String[] args) {
        int[] input = XRead.fileAsCsvLineToIntArray(filePath("day07"), ",");
        int minPosition = XUtils.minFromArray(input).value;
        int maxPosition = XUtils.maxFromArray(input).value;

        int minFuel = Integer.MAX_VALUE;
        for (int target = minPosition; target <= maxPosition; target++) {
            int currentFuel = 0;
            for (int position : input) {
                currentFuel += XNum.abs(position - target);
            }
            if (currentFuel < minFuel) {
                minFuel = currentFuel;
            }
        }

        Log.part1(minFuel);

        minFuel = Integer.MAX_VALUE;
        for (int target = minPosition; target <= maxPosition; target++) {
            int currentFuel = 0;
            for (int position : input) {
                int baseFuel = XNum.abs(position - target);
                currentFuel += (baseFuel * (baseFuel + 1) / 2);
            }
            if (currentFuel < minFuel) {
                minFuel = currentFuel;
            }
        }

        Log.part2(minFuel);
    }

}
