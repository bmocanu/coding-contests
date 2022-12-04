package ws.bmocanu.aoc.utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import ws.bmocanu.aoc.flex.Pointer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class XUtils {

    public static long getCantorPairingValue(int a1, int a2) {
        return (long) (a1 + a2) * (a1 + a2 + 1) / 2 + a2;
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

    public static boolean charIsLetter(char chr) {
        return (chr >= 'A' && chr <= 'Z') || (chr >= 'a' && chr <= 'z');
    }

    public static boolean charIsDigit(char chr) {
        return chr >= '0' && chr <= '9';
    }

    public static boolean stringIsNumber(String value) {
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if ((c < '0' || c > '9') && (c != '-')) {
                return false;
            }
        }
        return true;
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
                if (padding > 0) {
                    builder.append(String.format("%" + padding + "d", anInt));
                } else {
                    builder.append(String.format("%d", anInt));
                }
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

    public static Long maxFromArray(long[] array) {
        long max = Long.MIN_VALUE;
        for (int index = 0; index < array.length; index++) {
            if (array[index] > max) {
                max = array[index];
            }
        }
        return max;
    }

    public static long maxFromCollection(Collection<AtomicLong> collection) {
        long max = Long.MIN_VALUE;
        for (AtomicLong current : collection) {
            long currentLong = current.get();
            if (currentLong > max) {
                max = currentLong;
            }
        }
        return max;
    }

    public static long minFromCollection(Collection<AtomicLong> collection) {
        long min = Long.MAX_VALUE;
        for (AtomicLong current : collection) {
            long currentLong = current.get();
            if (currentLong < min) {
                min = currentLong;
            }
        }
        return min;
    }

    public static Pointer<Integer> maxFromList(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int pos = 0;
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index) > max) {
                max = list.get(index);
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

    public static Pointer<Integer> minFromList(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int pos = 0;
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index) < min) {
                min = list.get(index);
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

    public static String flipString(String str) {
        StringBuilder builder = new StringBuilder(str.length());
        for (int index = str.length() - 1; index >= 0; index--) {
            builder.append(str.charAt(index));
        }
        return builder.toString();
    }

    public static long mapStringCharsToBinaryToInt(String str, char chrFor1) {
        long result = 0;
        long mult = 1;
        for (int index = str.length() - 1; index >= 0; index--) {
            if (str.charAt(index) == chrFor1) {
                result += mult;
            }
            mult = mult * 2;
        }
        return result;
    }

    public static List<String> splitCsvStringToStringList(String input, String delimiter) {
        List<String> result = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input.trim(), delimiter);
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken().trim());
        }
        return result;
    }

    public static List<Integer> splitCsvStringToIntList(String str, String delimiter) {
        List<Integer> result = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, delimiter);
        while (tokenizer.hasMoreTokens()) {
            result.add(Integer.parseInt(tokenizer.nextToken().trim()));
        }
        return result;
    }

    public static String[] toStringArray(List<String> strList) {
        return strList.toArray(new String[0]);
    }

    public static int[] toIntArray(List<Integer> intList) {
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] toIntArrayFromStringList(List<String> strList) {
        return strList.stream().mapToInt(Integer::parseInt).toArray();
    }

    public static int lettersInCommon(String str1, String str2) {
        char[] str1Chars = str1.toCharArray();
        char[] str2Chars = str2.toCharArray();
        int result = 0;
        for (char ch1 : str1Chars) {
            for (char ch2 : str2Chars) {
                if (ch1 == ch2) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }

    public static String sortChars(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static List<Integer> charArrayAsDigitsToIntList(char[] digits) {
        List<Integer> list = new LinkedList<>();
        for (char c : digits) {
            list.add(c - '0');
        }
        return list;
    }

    public static String hexToBinary(String hexInput) {
        StringBuilder builder = new StringBuilder(hexInput.length() * 4);
        for (char c : hexInput.toCharArray()) {
            builder.append(hexToBinaryMap.get(c));
        }
        return builder.toString();
    }

    public static Map<Character, String> hexToBinaryMap = new HashMap<>() {{
        put('0', "0000");
        put('1', "0001");
        put('2', "0010");
        put('3', "0011");
        put('4', "0100");
        put('5', "0101");
        put('6', "0110");
        put('7', "0111");
        put('8', "1000");
        put('9', "1001");
        put('A', "1010");
        put('B', "1011");
        put('C', "1100");
        put('D', "1101");
        put('E', "1110");
        put('F', "1111");
    }};

}
