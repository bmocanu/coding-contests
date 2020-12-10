package ws.bmocanu.aoc.ed2020;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;

public class Day10 {

    private static int[] differences;
    private static int[] intNumbers;
    private static int count;
    private static int maxJoltage;

    public static void main(String[] args) {
        List<Integer> numbers = FileUtils.fileAsIntPerLineToIntList("day10");
        Collections.sort(numbers);
        System.out.println(numbers);

        differences = new int[numbers.size()];
        Arrays.fill(differences, -1);
        solvePart1(numbers, 0, 0);

        numbers.add(0, 0);
//        int[] combs = new int[numbers.size()];
//        for (int i1 = 1; i1 < combs.length; i1++) {
//            int n1 = numbers.get(i1);
//            for (int i2 = 0; i2 < i1; i2++) {
//                int n2 = numbers.get(i2);
//                if (n1 - n2 <= 3) {
//                    combs[i1] += combs[i2] + 1;
//                }
//            }
//        }

        count = 0;
        for (int index = 0; index < numbers.size(); index++) {
            for (int index2 = index + 1; index2 < numbers.size(); index2++) {
                if (numbers.get(index2) - numbers.get(index) <= 3) {
                    count++;
                }
            }
        }
        System.out.println(count);
//        solvePart2(0);
//        Log.part2(count);
    }

    public static void solvePart1(List<Integer> numbers, int sourceJoltage, int adaptersUsed) {
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
            return;
        }
        for (int index = 0; index < numbers.size(); index++) {
            int number = numbers.get(index);
            if ((number - sourceJoltage >= 1 && number - sourceJoltage <= 3) && differences[index] == -1) {
                differences[index] = number - sourceJoltage;
                solvePart1(numbers, number, adaptersUsed + 1);
                differences[index] = -1;
            }
        }

    }

    public static void solvePart2(int sourceJoltage) {
        if (sourceJoltage == maxJoltage) {
            count++;
            System.out.println(count);
            return;
        }
        for (int index = 0; index < intNumbers.length; index++) {
            int number = intNumbers[index];
            if ((number - sourceJoltage >= 1 && number - sourceJoltage <= 3) && differences[index] == -1) {
                differences[index] = 1;
                solvePart2(number);
                differences[index] = -1;
            }
        }

    }

}
