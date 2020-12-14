package ws.bmocanu.aoc.utils;

import java.math.BigInteger;
import java.util.*;

import ws.bmocanu.aoc.flex.Pointer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Utils {

    public static long getCantorPairingValue(int a1, int a2) {
        return (long) (a1 + a2) * (a1 + a2 + 1) / 2 + a2;
    }

    public static int max(int a1, int a2) {
        return Math.max(a1, a2);
    }

    public static int min(int a1, int a2) {
        return Math.min(a1, a2);
    }

    public static long max(long a1, long a2) {
        return Math.max(a1, a2);
    }

    public static long min(long a1, long a2) {
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

    /*
    20 21 22 23 24 25
    14 15 16 17 18 19
     8  9 10 11 12 13
     2  3  4  5  6  7

   |           |               |                 |                 |                 |
  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34

    2
    max - min + v + 1 = 8
    max - min + v + 1 = 14
    max - min + v + 1 = 20

    34
    v - (max - min) - 1 = 28
    v - (max - min) - 1 = 22
    */
    public static int cycleInt(int value, int minValue, int maxValue) {
        if (value >= minValue && value <= maxValue) {
            return value;
        }
        int newValue = value;
        int interval = maxValue - minValue;
        while (newValue < minValue) {
            newValue = interval + newValue + 1;
        }
        while (newValue > maxValue) {
            newValue = newValue - interval - 1;
        }
        return newValue;
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

    public static long smallestCommonMultiplier(int[] numbers) {
        Map<Integer, Integer> finalMap = new HashMap<>();
        for (int number : numbers) {
            Map<Integer, Integer> currentNumberMap = primeFactors(number);
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
        long result = 1;
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

    public static boolean charIsDigit(char chr) {
        return chr >= '0' && chr <= '9';
    }

    public static String intArrayToString(int[] array) {
        StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append('[');
        for (int i : array) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append(i);
        }
        builder.append(']');
        return builder.toString();
    }

    public static String longArrayToString(long[] array) {
        StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append('[');
        for (long i : array) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append(i);
        }
        builder.append(']');
        return builder.toString();
    }

    public static String byteArrayToString(byte[] array) {
        StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append('[');
        for (byte i : array) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append(i);
        }
        builder.append(']');
        return builder.toString();
    }

    public static String stringArrayToString(String[] array) {
        StringBuilder builder = new StringBuilder(array.length * 10);
        builder.append('[');
        for (String str : array) {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append('[').append(str).append(']');
        }
        builder.append(']');
        return builder.toString();
    }

    public static String intMatrixToString(int[][] mtx, int padding) {
        StringBuilder builder = new StringBuilder(mtx.length * (mtx[0].length * padding + 3));
        String horizontalBorder = "+" + "-".repeat(mtx[0].length * padding) + "+\n";
        builder.append(horizontalBorder);
        for (int[] ints : mtx) {
            builder.append("|");
            for (int anInt : ints) {
                builder.append(String.format("%" + padding + "d", anInt));
            }
            builder.append("|\n");
        }
        builder.append(horizontalBorder);
        return builder.toString();
    }

    public static String charMatrixToString(char[][] mtx) {
        StringBuilder builder = new StringBuilder(mtx.length * (mtx[0].length + 3));
        String horizontalBorder = "+" + "-".repeat(mtx[0].length) + "+\n";
        builder.append(horizontalBorder);
        for (char[] chars : mtx) {
            builder.append("|");
            for (char aChar : chars) {
                builder.append(aChar);
            }
            builder.append("|\n");
        }
        builder.append(horizontalBorder);
        return builder.toString();
    }

    public static String stringMatrixToString(String[][] mtx, int padding) {
        StringBuilder builder = new StringBuilder(mtx.length * (mtx[0].length * padding + 3));
        String horizontalBorder = "+" + "-".repeat(mtx[0].length * padding) + "+\n";
        builder.append(horizontalBorder);
        for (String[] strings : mtx) {
            builder.append("|");
            for (String aString : strings) {
                builder.append(String.format("%" + padding + "s", aString));
            }
            builder.append("|\n");
        }
        builder.append(horizontalBorder);
        return builder.toString();
    }

    public static Pointer<Integer> maxFromArray(int[] array) {
        int max = Integer.MIN_VALUE;
        int pos = 0;
        for (int index = 0; index < array.length; index++) {
            if (array[index] > max) {
                max = array[index];
                pos = index;
            }
        }
        return new Pointer<>(max, pos);
    }

    public static Pointer<Integer> minFromArray(int[] array) {
        int min = Integer.MAX_VALUE;
        int pos = 0;
        for (int index = 0; index < array.length; index++) {
            if (array[index] < min) {
                min = array[index];
                pos = index;
            }
        }
        return new Pointer<>(min, pos);
    }

    public static String strPadding(String value, int padding, String padWith) {
        if (padding > value.length()) {
            return padWith.repeat(padding - value.length()) + value;
        }
        return value;
    }

    public static boolean stringOneOf(String input, String... values) {
        Set<String> valueSet = new HashSet<>(Arrays.asList(values));
        return valueSet.contains(input);
    }

    public static boolean intOneOf(int valueToCheck, int... values) {
        for (int currentValue : values) {
            if (currentValue == valueToCheck) {
                return true;
            }
        }
        return false;
    }

    public static boolean charOneOf(char chrToCheck, char... values) {
        for (char currentValue : values) {
            if (currentValue == chrToCheck) {
                return true;
            }
        }
        return false;
    }

    public static int[] intToBinary(int input) {
        return longToBinaryWithPadding(input, 0);
    }

    public static int[] intToBinaryWithPadding(int input, int finalLength) {
        return longToBinaryWithPadding(input, finalLength);
    }

    public static int[] longToBinary(long input) {
        return longToBinaryWithPadding(input, 0);
    }

    public static int[] longToBinaryWithPadding(long input, int finalLength) {
        String binary = Long.toBinaryString(input);
        if (finalLength > 0 && finalLength > binary.length()) {
            binary = "0".repeat(finalLength - binary.length()) + binary;
        }
        int[] result = new int[binary.length()];
        for (int index = 0; index < binary.length(); index++) {
            result[index] = binary.charAt(index) - '0';
        }
        return result;
    }

    public static long binaryToLong(int[] binary) {
        long mult = 1;
        long result = 0;
        for (int index = binary.length - 1; index >= 0; index--) {
            result += binary[index] * mult;
            mult *= 2;
        }
        return result;
    }

    public static BigInteger binaryToBigInt(int[] binary) {
        BigInteger mult = BigInteger.ONE;
        BigInteger result = BigInteger.ZERO;
        for (int index = binary.length - 1; index >= 0; index--) {
            result = result.add(mult.multiply(BigInteger.valueOf(binary[index])));
            mult = mult.multiply(BigInteger.TWO);
        }
        return result;
    }

}
