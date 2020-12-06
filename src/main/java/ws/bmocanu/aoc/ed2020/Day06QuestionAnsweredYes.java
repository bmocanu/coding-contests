package ws.bmocanu.aoc.ed2020;

import java.util.*;

import ws.bmocanu.aoc.flex.FlexChars;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;

public class Day06QuestionAnsweredYes {

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList("day06");

        FlexChars fc = new FlexChars();
        int total = 0;
        for (String line : stringLines) {
            line = line.trim();
            if (line.isEmpty()) {
                total += fc.size();
                fc.reset();
            } else {
                fc.addChars(line);
            }
        }
        total += fc.size();

        Log.part1(total);

        fc.reset();
        int total2 = 0;
        int groupSize = 0;
        for (String line : stringLines) {
            line = line.trim();
            if (line.isEmpty()) {
                total2 += fc.getCharsWithCount(groupSize).size();
                fc.reset();
                groupSize = 0;
            } else {
                fc.addChars(line);
                groupSize++;
            }
        }
        total2 += fc.getCharsWithCount(groupSize).size();

        Log.part2(total2);
    }

}
