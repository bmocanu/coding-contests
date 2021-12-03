package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day09XmasPrev25Numbers extends SolutionBase {

    public static void main(String[] args) {
        List<Long> numbers = XRead.fileAsLongPerLineToLongList(filePath("day09"));

        long numberP1 = 0;
        int numberP1Index = 0;
        int length = 25;
        for (int index = length; index < numbers.size(); index++) {
            long number = numbers.get(index);
            boolean isTheSum = false;
            for (int c1 = index - length; c1 < index; c1++) {
                for (int c2 = index - length; c2 < c1; c2++) {
                    if (numbers.get(c1) + numbers.get(c2) == number) {
                        isTheSum = true;
                    }
                }
            }
            if (!isTheSum) {
                Log.part1(number);
                numberP1 = number;
                numberP1Index = index;
                break;
            }
        }

        top_loop:
        for (int c1 = 0; c1 < numbers.size(); c1++) {
            for (int c2 = 0; c2 < c1; c2++) {
                long sum = 0;
                long min = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                for (int c3 = c2; c3 <= c1; c3++) {
                    if (c3 != numberP1Index) {
                        long currentNum = numbers.get(c3);
                        sum += currentNum;
                        min = XUtils.min(min, currentNum);
                        max = XUtils.max(max, currentNum);
                    }
                }
                if (sum == numberP1) {
                    Log.part2(min + max);
                    break top_loop;
                }
            }
        }

    }

}
