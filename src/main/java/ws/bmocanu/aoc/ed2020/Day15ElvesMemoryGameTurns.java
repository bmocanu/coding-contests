package ws.bmocanu.aoc.ed2020;

import java.util.Arrays;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day15ElvesMemoryGameTurns extends SolutionBase {

    public static int OFFSET = 100_000_000;
    public static int[] cache = new int[OFFSET * 2];

    public static void main(String[] args) {
        List<Integer> intList = XRead.fileAsCsvLineToIntList(filePath("day15"), ",");
        Arrays.fill(cache, -1);

        for (int index = 1; index <= intList.size(); index++) {
            int number = intList.get(index - 1);
            cache[number] = index;
            cache[number + OFFSET] = -1;
        }

        int lastNumber = intList.get(intList.size() - 1);
        int turnNumber = intList.size() + 1;
        while (turnNumber <= 30000000) {
            if (turnNumber == 2021) {
                Log.part1(lastNumber);
            }
            lastNumber = getNewNumber(turnNumber, lastNumber);
            turnNumber++;
        }

        Log.part2(lastNumber);
    }

    public static int getNewNumber(int turnNumber, int lastNumber) {
        int newNumber = 0;
        if (cache[OFFSET + lastNumber] > 0) {
            newNumber = cache[lastNumber] - cache[OFFSET + lastNumber];
        }
        if (cache[newNumber] < 0) {
            cache[newNumber] = turnNumber;
        } else {
            cache[OFFSET + newNumber] = cache[newNumber];
            cache[newNumber] = turnNumber;
        }
        return newNumber;
    }

}
