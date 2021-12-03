package ws.bmocanu.aoc.ed2021;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XBinary;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day03BinaryDiagnosticReport extends SolutionBase {

    public static void main(String[] args) {
        List<char[]> input = XRead.fileAsCharMatrixToCharArrayList(filePath("day03"));
        int numberLength = input.get(0).length;
        char[] gamma = new char[numberLength];
        char[] eps = new char[numberLength];
        for (int col = 0; col < numberLength; col++) {
            int[] onesAndZeroes = countOnesAndZeroesPerColumn(input, col);
            if (onesAndZeroes[1] > onesAndZeroes[0]) {
                gamma[col] = '1';
                eps[col] = '0';
            } else {
                gamma[col] = '0';
                eps[col] = '1';
            }
        }
        Log.part1(XBinary.toInt(gamma) * XBinary.toInt(eps));

        List<char[]> oxygen = new ArrayList<>(input);
        int pos = 0;
        while (oxygen.size() > 1) {
            List<char[]> newList = new ArrayList<>();
            int[] onesAndZeroes = countOnesAndZeroesPerColumn(oxygen, pos);
            if (onesAndZeroes[1] >= onesAndZeroes[0]) {
                for (char[] line : oxygen) {
                    if (line[pos] == '1') {
                        newList.add(line);
                    }
                }
            } else {
                for (char[] line : oxygen) {
                    if (line[pos] == '0') {
                        newList.add(line);
                    }
                }
            }
            oxygen = newList;
            pos++;
        }

        List<char[]> co2 = new ArrayList<>(input);
        pos = 0;
        while (co2.size() > 1) {
            List<char[]> newList = new ArrayList<>();
            int[] onesAndZeroes = countOnesAndZeroesPerColumn(co2, pos);
            if (onesAndZeroes[1] >= onesAndZeroes[0]) {
                for (char[] line : co2) {
                    if (line[pos] == '0') {
                        newList.add(line);
                    }
                }
            } else {
                for (char[] line : co2) {
                    if (line[pos] == '1') {
                        newList.add(line);
                    }
                }
            }
            co2 = newList;
            pos++;
        }
        Log.part2(XBinary.toInt(oxygen.get(0)) * XBinary.toInt(co2.get(0)));
    }

    private static int[] countOnesAndZeroesPerColumn(List<char[]> lines, int col) {
        int[] result = new int[2];
        for (char[] line : lines) {
            if (line[col] == '0') {
                result[0]++;
            }
            if (line[col] == '1') {
                result[1]++;
            }
        }
        return result;
    }

}
