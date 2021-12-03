package ws.bmocanu.aoc.ed2020;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.SReg;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day14BitMaskingAddresses extends SolutionBase {

    public static class Command {
        int address;
        long value;
    }

    public static Map<BigInteger, BigInteger> mem = new HashMap<>();

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day14"));
        SBind binder = new SBind("mem\\[(\\d+)] = (\\d+)", "address", "value");
        String maskString;
        int[] mask = new int[0];
        for (String line : stringLines) {
            if (line.startsWith("mask")) {
                maskString = SReg.parse("mask = ([01X]+)", line).getString(1);
                mask = getBitMask(maskString);
            } else {
                Command command = binder.bind(line, Command.class);
                mem.put(BigInteger.valueOf(command.address), part1GetBigWithMask(command.value, mask));
            }
        }

        Log.part1(getMemSum()); // 8570568288597

        mem.clear();
        for (String line : stringLines) {
            if (line.startsWith("mask")) {
                maskString = SReg.parse("mask = ([01X]+)", line).getString(1);
                mask = getBitMask(maskString);
            } else {
                Command command = binder.bind(line, Command.class);
                List<BigInteger> addresses = new ArrayList<>();
                part2GetAddresses(command.address, mask, addresses);
                for (BigInteger address : addresses) {
                    mem.put(address, BigInteger.valueOf(command.value));
                }
            }
        }

        Log.part2(getMemSum());
    }

    // ----------------------------------------------------------------------------------------------------

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

    public static BigInteger part1GetBigWithMask(long inputNumber, int[] mask) {
        int[] binary = XUtils.longToBinaryWithPadding(inputNumber, mask.length);
        int[] result = new int[mask.length];
        for (int index = 0; index < mask.length; index++) {
            if (mask[index] != -1) {
                result[index] = mask[index];
            } else {
                result[index] = binary[index];
            }
        }
        return XUtils.binaryToBigInt(result);
    }

    public static void part2GetAddresses(long inputNumber, int[] mask, List<BigInteger> addresses) {
        int[] binary = XUtils.longToBinaryWithPadding(inputNumber, mask.length);
        int[] result = new int[mask.length];
        for (int index = 0; index < mask.length; index++) {
            if (mask[index] == 0) {
                result[index] = binary[index];
            } else {
                result[index] = mask[index];
            }
        }
        part2GenerateFloatingBitAddresses(result, 0, addresses);
    }

    public static void part2GenerateFloatingBitAddresses(int[] input, int inputIndex, List<BigInteger> addresses) {
        if (inputIndex >= input.length) {
            addresses.add(XUtils.binaryToBigInt(input));
            return;
        }
        if (input[inputIndex] == -1) {
            input[inputIndex] = 0;
            part2GenerateFloatingBitAddresses(input, inputIndex + 1, addresses);
            input[inputIndex] = 1;
            part2GenerateFloatingBitAddresses(input, inputIndex + 1, addresses);
            input[inputIndex] = -1;
        } else {
            part2GenerateFloatingBitAddresses(input, inputIndex + 1, addresses);
        }
    }

    public static BigInteger getMemSum() {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger value : mem.values()) {
            sum = sum.add(value);
        }
        return sum;
    }

}
