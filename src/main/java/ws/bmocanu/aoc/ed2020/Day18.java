package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day18 extends SolutionBase {

    private static long typeParStart = -10;
    private static long typeParEnd = -20;
    private static long typeAdd = -30;
    private static long typeMult = -40;

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day18"));
        long sum1 = 0;
        long sum2 = 0;
        for (String line : stringLines) {
            line = line.replaceAll("\\s+", "");
            List<Long> parsedList = parseToLongs(line);
            sum2 += calculate(new ArrayList<>(parsedList), true);
        }

        Log.part1(sum1); // 69490582260
        Log.part2(sum2); // 362464596624526
    }

    private static List<Long> parseToLongs(String input) {
        List<Long> result = new ArrayList<>();
        long term = 0;
        boolean hasTerm = false;
        for (int index = 0; index < input.length(); index++) {
            if (Utils.charIsDigit(input.charAt(index))) {
                term = term * 10 + (input.charAt(index) - '0');
                hasTerm = true;
            } else {
                if (hasTerm) {
                    result.add(term);
                    term = 0;
                    hasTerm = false;
                }
                switch (input.charAt(index)) {
                    case '+':
                        result.add(typeAdd);
                        break;
                    case '*':
                        result.add(typeMult);
                        break;
                    case '(':
                        result.add(typeParStart);
                        break;
                    case ')':
                        result.add(typeParEnd);
                        break;
                }
            }
        }
        result.add(term);
        return result;
    }

    private static long calculate(List<Long> input, boolean orderMatters) {
        int startPar = 0;
        int parDepth = 0;
        int index = 0;
        while (index < input.size()) {
            long curInput = input.get(index);
            if (curInput == typeParStart) {
                if (parDepth == 0) {
                    startPar = index;
                }
                parDepth++;
            } else if (curInput == typeParEnd) {
                parDepth--;
                if (parDepth == 0) {
                    long result = calculate(
                            new ArrayList<>(input.subList(startPar + 1, index)),
                            orderMatters);
                    for (int remIndex = 0; remIndex < index - startPar + 1; remIndex++) {
                        input.remove(startPar);
                    }
                    input.add(startPar, result);
                    startPar = 0;
                    index = -1;
                }
            }
            index++;
        }

        if (!orderMatters) {

        } else {
            long[] targetSigns = new long[]{typeAdd, typeMult};
            for (long sign : targetSigns) {
                index = 0;
                while (index < input.size()) {
                    long curInput = input.get(index);
                    if (curInput == sign && sign == typeAdd) {
                        long result = input.get(index - 1) + input.get(index + 1);
                        input.remove(index - 1);
                        input.remove(index - 1);
                        input.remove(index - 1);
                        input.add(index - 1, result);
                        index -= 2;
                    } else if (curInput == sign && sign == typeMult) {
                        long result = input.get(index - 1) * input.get(index + 1);
                        input.remove(index - 1);
                        input.remove(index - 1);
                        input.remove(index - 1);
                        input.add(index - 1, result);
                        index -= 2;
                    }
                    index++;
                }
            }
        }

        return input.get(0);
    }

}
