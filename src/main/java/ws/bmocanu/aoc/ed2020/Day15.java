package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day15 extends SolutionBase {

    public static class Tuple {
        int back1;
        int back2;

        public Tuple(int back1, int back2) {
            this.back1 = back1;
            this.back2 = back2;
        }

        public void set(int newB1, int newB2) {
            back1 = newB1;
            back2 = newB2;
        }
    }

    public static Map<Integer, Tuple> cache = new HashMap<>();

    public static void main(String[] args) {
        List<Integer> intList = FileUtils.fileAsCsvLineToIntList(filePath("day15"), ",");
        List<Integer> numberList = new ArrayList<>();

        int index = 1;
        for (Integer number : intList) {
            cache.put(number, new Tuple(index, -1));
            index++;
        }

//        Tuple zeroTuple = cache.get(0);
//        if (zeroTuple == null) {
//            cache.put(0, new Tuple(intList.size(), -1));
//        } else {
//            zeroTuple.set(intList.size(), zeroTuple.back1);
//        }

//        numberList.addAll(intList);
//        numberList.add(0);
//        int lastNumber = numberList.get(numberList.size() - 1);
        int lastNumber = intList.get(intList.size() - 1);
        int turnNumber = intList.size() + 1;
//        while (turnNumber < 30000000) {
        while (turnNumber <= 2020) {
            if (turnNumber % 10000 == 0) {
                System.out.println(turnNumber);
            }
            int newNumber = getGuessResult(turnNumber, lastNumber);
            Tuple newNumberTuple = cache.get(newNumber);
            if (newNumberTuple != null) {
                newNumberTuple.set(turnNumber, newNumberTuple.back1);
            }
            numberList.add(newNumber);
            lastNumber = newNumber;
            turnNumber++;
        }

        Log.part1(lastNumber);

        Log.part2(0);
    }

    public static int getGuessResult(int turnNumber, int number) {
        Tuple tuple = cache.get(number);
        if (tuple == null) {
            cache.put(number, new Tuple(turnNumber, -1));
            return 0;
        } else if (tuple.back2 < 0) {
            int result = 0;
//            tuple.set(turnNumber, tuple.back1);
            return result;
        } else {
            int result = tuple.back1 - tuple.back2;
//            tuple.set(turnNumber, tuple.back1);
            return result;
        }
//
//
//        int back1 = -1;
//        int back2 = -1;
//        for (int index = numberList.size() - 1; index >= 0; index--) {
//            if (numberList.get(index) == number) {
//                if (back1 < 0) {
//                    back1 = index;
//                } else {
//                    back2 = index;
//                    break;
//                }
//            }
//        }
//        if (back1 < 0 || back2 < 0) {
//            return 0;
//        } else {
//            return back1 - back2;
//        }
    }

}
