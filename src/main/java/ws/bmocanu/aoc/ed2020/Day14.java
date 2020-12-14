package ws.bmocanu.aoc.ed2020;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.support.SReg;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day14 extends SolutionBase {

    public static class Command {
        int address;
        long value;
    }

    // 11797682288034

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day14"));
        SBinder binder = new SBinder("mem\\[(\\d+)] = (\\d+)", "address", "value");

        BigInteger[] mem = new BigInteger[100000];
        Arrays.fill(mem, BigInteger.ZERO);
        String maskString;
        int[] mask = null;
        for (String line : stringLines) {
            if (line.startsWith("mask")) {
                maskString = SReg.parse("mask = ([01X]+)", line).getString(1);
                mask = getBitMask(maskString);
            } else {
                Command command = binder.bind(line, Command.class);
                mem[command.address] = getBigWithMask(command.value, mask);
                System.out.println("[" + command.address + "] = " + mem[command.address]);
            }
        }

        BigInteger sum = BigInteger.ZERO;
        for (BigInteger memElem : mem) {
            sum = sum.add(memElem);
        }

        Log.part1(sum);

        Log.part2(0);
    }

    public static int[] getBitMask(String maskString) {
        int[] result = new int[maskString.length()];
        Arrays.fill(result, -1);
        for (int index = 0; index < result.length; index++) {
            char chr = maskString.charAt(index);
            if (chr != 'X') {
                result[index] = (chr - '0');
            }
        }
        return result;
    }

    public static BigInteger getBigWithMask(long inputInt, int[] mask) {
        if (inputInt < 0) {
            throw new IllegalStateException("Overflow");
        }
        String binary = Long.toBinaryString(inputInt);
        binary = "0".repeat(mask.length - binary.length()) + binary;
        int[] result = new int[mask.length];
        for (int index = 0; index < mask.length; index++) {
            if (mask[index] != -1) {
                result[index] = mask[index];
            } else {
                result[index] = binary.charAt(index) - '0';
            }
        }
        BigInteger mult = BigInteger.ONE;
        BigInteger resultBig = BigInteger.ZERO;
        for (int index = result.length - 1; index >= 0; index--) {
            resultBig = resultBig.add(mult.multiply(BigInteger.valueOf(result[index])));
            mult = mult.multiply(BigInteger.TWO);
        }

        return resultBig;
    }

}
