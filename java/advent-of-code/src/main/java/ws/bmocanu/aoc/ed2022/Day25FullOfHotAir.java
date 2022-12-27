package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.math.BigInteger;
import java.util.List;

public class Day25FullOfHotAir extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day25"));

        BigInteger inputNrsSum = BigInteger.ZERO;
        for (String line : input) {
            inputNrsSum = inputNrsSum.add(BigInteger.valueOf(decodeFromSnafu(line)));
        }

        System.out.println("Sum=" + inputNrsSum);
        System.out.println("ToSnafu=" + encodeToSnafu(inputNrsSum.longValue()));
        System.out.println("FromSnafu=" + decodeFromSnafu(encodeToSnafu(inputNrsSum.longValue())));

        System.out.println(encodeToSnafu(inputNrsSum.longValue()));
    }

    private static long decodeFromSnafu(String nrStr) {
        long result = 0;
        long factor = 1;

        for (int index = nrStr.length() - 1; index >= 0; index--) {
            long multiplier = 0;
            char chr = nrStr.charAt(index);
            if (Character.isDigit(chr)) {
                multiplier = chr - '0';
            } else if (chr == '-') {
                multiplier = -1;
            } else if (chr == '=') {
                multiplier = -2;
            }
            result += factor * multiplier;
            factor = factor * 5;
        }
        return result;
    }

    private static String encodeToSnafu(long nr) {
        if (nr == 0) {
            return "0";
        }
        StringBuilder snafu = new StringBuilder(20);
        while (nr != 0) {
            int remainder = (int) (nr % 5);
            switch (remainder) {
                case 0:
                    snafu.append('0');
                    nr = nr / 5;
                    break;
                case 1:
                    snafu.append('1');
                    nr = nr / 5;
                    break;
                case 2:
                    snafu.append('2');
                    nr = nr / 5;
                    break;
                case 3:
                    snafu.append('=');
                    nr = nr / 5 + 1;
                    break;
                case 4:
                    snafu.append('-');
                    nr = nr / 5 + 1;
                    break;
            }
        }
        return snafu.reverse().toString();
    }

}
