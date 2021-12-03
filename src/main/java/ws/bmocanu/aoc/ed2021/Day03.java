package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03 extends SolutionBase {

    public static void main(String[] args) {
        char[][] input = FileUtils.fileAsCharMatrixToCharMatrix(filePath("day03"));
        char[] gamma = new char[input[0].length];
        char[] eps = new char[input[0].length];
        for(int col = 0; col < input[0].length; col++) {
            int ones = 0;
            int zeros = 0;
            for(int line = 0; line < input.length; line++) {
                if (input[line][col] == '0') {
                    zeros++;
                }
                if (input[line][col] == '1') {
                    ones++;
                }
            }
            if (ones > zeros) {
                gamma[col] = '1';
                eps[col] = '0';
            } else {
                gamma[col] = '0';
                eps[col] = '1';
            }
        }
        int gammaInt = Integer.parseInt(new String(gamma), 2);
        int epsInt = Integer.parseInt(new String(eps), 2);
        Log.part1(gammaInt * epsInt);

        List<char[]> oxygen = new ArrayList<>();

        oxygen.addAll(Arrays.asList(input));
        int pos = 0;
        while(oxygen.size() > 1) {
            List<char[]> newList = new ArrayList<>();
            int ones = 0;
            int zeros = 0;
            for(char[] line : oxygen) {
                if (line[pos] == '0') {
                    zeros++;
                }
                if (line[pos] == '1') {
                    ones++;
                }
            }
            if (ones >= zeros) {
                for(char[] line : oxygen) {
                    if (line[pos] == '1') {
                        newList.add(line);
                    }
                }
            } else {
                for(char[] line : oxygen) {
                    if (line[pos] == '0') {
                        newList.add(line);
                    }
                }
            }
            oxygen = newList;
            pos++;
        }

        List<char[]> co2 = new ArrayList<>();
        co2.addAll(Arrays.asList(input));
        pos = 0;
        while(co2.size() > 1) {
            List<char[]> newList = new ArrayList<>();
            int ones = 0;
            int zeros = 0;
            for(char[] line : co2) {
                if (line[pos] == '0') {
                    zeros++;
                }
                if (line[pos] == '1') {
                    ones++;
                }
            }
            if (ones >= zeros) {
                for(char[] line : co2) {
                    if (line[pos] == '0') {
                        newList.add(line);
                    }
                }
            } else {
                for(char[] line : co2) {
                    if (line[pos] == '1') {
                        newList.add(line);
                    }
                }
            }
            co2 = newList;
            pos++;
        }

        int oxygenInt = Integer.parseInt(new String(oxygen.get(0)), 2);
        int co2Int = Integer.parseInt(new String(co2.get(0)), 2);
        Log.part2(oxygenInt * co2Int);
    }

}
