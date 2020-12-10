package ws.bmocanu.aoc.ed2020;

import java.util.Collections;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day10JoltsAndAdapters extends SolutionBase {

    public static void main(String[] args) {
        List<Integer> numbers = FileUtils.fileAsIntPerLineToIntList(filePath("day10"));
        Collections.sort(numbers);

        int jolt1 = 1; // add the default increment from 0 jolts
        int jolt3 = 1; // add the default increment of 3 jolts
        for (int index = 0; index + 1 < numbers.size(); index++) {
            if (numbers.get(index + 1) - numbers.get(index) == 1) {
                jolt1++;
            } else {
                jolt3++;
            }
        }
        Log.part1(jolt1 * jolt3);

        numbers.add(0, 0);
        long[] combs = new long[numbers.size()];
        combs[0] = 1;
        combs[1] = 1;
        for (int index = 2; index < numbers.size(); index++) {
            int numb = numbers.get(index);
            combs[index] = combs[index - 1];
            if (numb - numbers.get(index - 2) <= 3) {
                combs[index] += combs[index - 2];
            }
            if (index > 2 && numb - numbers.get(index - 3) <= 3) {
                combs[index] += combs[index - 3];
            }
        }
        Log.part2(combs[combs.length - 1]);
    }

}
