package ws.bmocanu.aoc.utils;

import java.math.BigInteger;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class XBinary {

    public static int toInt(char[] number) {
        return Integer.parseInt(new String(number), 2);
    }

    public static long toLong(char[] number) {
        return Long.parseLong(new String(number), 2);
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
