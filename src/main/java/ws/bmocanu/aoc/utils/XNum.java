package ws.bmocanu.aoc.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class XNum {

    public static long sumOfLongArray(long[] array) {
        long sum = 0;
        for (long elem : array) {
            sum += elem;
        }
        return sum;
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
                finalMap.merge(factor, repetition, (a, b) -> max(b, a));
            }
        }
        long result = 1;
        for (Map.Entry<Integer, Integer> primeFactor : finalMap.entrySet()) {
            result *= pow(primeFactor.getKey(), primeFactor.getValue());
        }
        return result;
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
