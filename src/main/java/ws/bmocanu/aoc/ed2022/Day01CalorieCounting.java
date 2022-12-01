package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day01CalorieCounting extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day01"));
        List<Integer> elfStat = new ArrayList<>();
        int currentStat = 0;
        for (String line : input) {
            if (line.length() > 0) {
                currentStat += Integer.parseInt(line);
            } else {
                elfStat.add(currentStat);
                currentStat = 0;
            }
        }

        elfStat.sort(Comparator.reverseOrder());
        Log.part1(elfStat.get(0));
        Log.part2(elfStat.get(0) + elfStat.get(1) + elfStat.get(2));
    }

}
