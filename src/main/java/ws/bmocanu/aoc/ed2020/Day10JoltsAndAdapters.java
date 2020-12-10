package ws.bmocanu.aoc.ed2020;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;

public class Day10JoltsAndAdapters {

    private static List<Integer> numbers;
    private static int[] differences;

    public static void main(String[] args) {
        numbers = FileUtils.fileAsIntPerLineToIntList("day10");
        Collections.sort(numbers);
        differences = new int[numbers.size()];
        Arrays.fill(differences, -1);
        solvePart1(0, 0);

        numbers.add(0, 0);
        long[] combs = new long[numbers.size()];
        combs[0] = 1;
        combs[1] = 1;
        for (int index = 2; index < numbers.size(); index++) {
            int numb = numbers.get(index);
            combs[index] = combs[index - 1];
            if (index > 1 && numb - numbers.get(index - 2) <= 3) {
                combs[index] += combs[index - 2];
            }
            if (index > 2 && numb - numbers.get(index - 3) <= 3) {
                combs[index] += combs[index - 3];
            }
        }
        Log.part2(combs[combs.length - 1]);
    }

    public static boolean solvePart1(int sourceJoltage, int adaptersUsed) {
        if (adaptersUsed == numbers.size()) {
            int jolt1 = 0;
            int jolt3 = 0;
            for (int diff : differences) {
                if (diff == 1) {
                    jolt1++;
                } else if (diff == 3) {
                    jolt3++;
                }
            }
            Log.part1(jolt1 * (jolt3 + 1));
            return true;
        }
        for (int index = 0; index < numbers.size(); index++) {
            int number = numbers.get(index);
            if ((number - sourceJoltage >= 1 && number - sourceJoltage <= 3) && differences[index] == -1) {
                differences[index] = number - sourceJoltage;
                if (solvePart1(number, adaptersUsed + 1)) {
                    return true;
                }
                differences[index] = -1;
            }
        }
        return false;
    }

}
