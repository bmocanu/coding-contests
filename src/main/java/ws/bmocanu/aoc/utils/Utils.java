package ws.bmocanu.aoc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Direction;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Utils {

    public static long getCantorPairingValue(int a1, int a2) {
        return (a1 + a2) * (a1 + a2 + 1) / 2 + a2;
    }

    public static Direction directionDelta0123(int dir) {
        switch (dir) {
            case 0:
                return Direction.from(0, -1);
            case 1:
                return Direction.from(1, 0);
            case 2:
                return Direction.from(0, 1);
            case 3:
                return Direction.from(-1, 0);
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    public static int max(int a1, int a2) {
        return Math.max(a1, a2);
    }

    public static int min(int a1, int a2) {
        return Math.min(a1, a2);
    }

    public static int abs(int a1) {
        return Math.abs(a1);
    }

    public static int pow(int x, int power) {
        int result = 1;
        for (int index = 0; index < power; index++) {
            result *= x;
        }
        return result;
    }

    public static int cycleInt(int value, int minValue, int maxValue) {
        if (value < minValue) {
            return maxValue;
        } else if (value > maxValue) {
            return minValue;
        } else {
            return value;
        }
    }

    public static int arrayToInt(int[] array) {
        int result = 0;
        int powerOfTen = 1;
        for (int index = array.length - 1; index >= 0; index--) {
            result += array[index] * powerOfTen;
            powerOfTen *= 10;
        }
        return result;
    }

    public static long arrayToLong(int[] array) {
        long result = 0;
        long powerOfTen = 1;
        for (int index = array.length - 1; index >= 0; index--) {
            result += array[index] * powerOfTen;
            powerOfTen *= 10;
        }
        return result;
    }

    public static Map<Integer, Integer> primeFactors(int value) {
        Map<Integer, Integer> result = new HashMap<>();
        int remainder = value;
        for (int divisor = 2; divisor <= remainder; divisor++) {
            int divTimes = 0;
            while (remainder > 1 && remainder % divisor == 0) {
                remainder = remainder / divisor;
                divTimes++;
            }
            if (divTimes > 0) {
                result.put(divisor, divTimes);
            }
        }
        return result;
    }

    public static int smallestCommonMultiplier(int[] numbers) {
        Map<Integer, Integer> finalMap = new HashMap<>();
        for (int numberIndex = 0; numberIndex < numbers.length; numberIndex++) {
            Map<Integer, Integer> currentNumberMap = primeFactors(numbers[numberIndex]);
            for (Map.Entry<Integer, Integer> currentNumberEntry : currentNumberMap.entrySet()) {
                Integer factor = currentNumberEntry.getKey();
                Integer repetition = currentNumberEntry.getValue();
                Integer finalRepetition = finalMap.get(factor);
                if (finalRepetition != null) {
                    finalMap.put(factor, max(repetition, finalRepetition));
                } else {
                    finalMap.put(factor, repetition);
                }
            }
        }
        int result = 1;
        for (Map.Entry<Integer, Integer> primeFactor : finalMap.entrySet()) {
            result *= pow(primeFactor.getKey(), primeFactor.getValue());
        }
        return result;
    }

    public static List<String> stringAsCsvToStringList(String content, String separator) {
        StringTokenizer tokenizer = new StringTokenizer(content, separator);
        List<String> stringList = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            stringList.add(tokenizer.nextToken().trim());
        }
        return stringList;
    }

    public static boolean charIsLetter(char chr) {
        return (chr >= 'A' && chr <= 'Z') || (chr >= 'a' && chr <= 'z');
    }

}
