package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day15ElvesMemoryGameTurns extends SolutionBase {

    public static class Tuple {
        int back1;
        int back2;

        public Tuple(int back1, int back2) {
            this.back1 = back1;
            this.back2 = back2;
        }

        public void shift(int newPos) {
            back2 = back1;
            back1 = newPos;
        }
    }

    public static Tuple[] cache = new Tuple[100000000];

    public static void main(String[] args) {
        List<Integer> intList = FileUtils.fileAsCsvLineToIntList(filePath("day15"), ",");

        int index = 1;
        for (Integer number : intList) {
            cache[number] = new Tuple(index, -1);
            index++;
        }

        int lastNumber = intList.get(intList.size() - 1);
        int turnNumber = intList.size() + 1;
        while (turnNumber <= 30000000) {
            if (turnNumber == 2021) {
                Log.part1(lastNumber);
            }
            int newNumber = getNewNumber(turnNumber, lastNumber);
            lastNumber = newNumber;
            turnNumber++;
        }

        Log.part2(lastNumber);
    }

    public static int getNewNumber(int turnNumber, int lastNumber) {
        Tuple lastNumberTuple = cache[lastNumber];
        int newNumber = 0;
        if (lastNumberTuple.back2 > 0) {
            newNumber = lastNumberTuple.back1 - lastNumberTuple.back2;
        }
        Tuple newNumberTuple = cache[newNumber];
        if (newNumberTuple == null) {
            cache[newNumber] = new Tuple(turnNumber, -1);
        } else {
            newNumberTuple.shift(turnNumber);
        }
        return newNumber;
    }

}
