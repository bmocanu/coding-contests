package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.HashMap;
import java.util.Map;

public class Day23CrabsMillionthCupGame extends SolutionBase {

    static class Cup {
        public int value;
        public Cup next;
    }

    static Cup firstCup = null;
    static Cup lastCup = null;
    static int minCup;
    static int maxCup;
    static Map<Integer, Cup> mapOfCups = new HashMap<>();

    public static void main(String[] args) {
        setCupsToInput("643719258");
        doTheRounds(100);
        Log.part1(printCupsStartingFrom1().substring(1));

        setCupsToInput("643719258");
        Cup cursor = lastCup;
        for (int cup = maxCup + 1; cup <= 1000000; cup++) {
            Cup newCup = new Cup();
            newCup.next = firstCup;
            cursor.next = newCup;
            cursor = newCup;
            cursor.value = cup;
            mapOfCups.put(cup, cursor);
        }
        maxCup = 1_000_000;

        doTheRounds(10_000_000);

        Cup cup1 = mapOfCups.get(1);
        Log.part2((long) cup1.next.value * cup1.next.next.value);
    }

    @SuppressWarnings("SameParameterValue")
    private static void setCupsToInput(String input) {
        minCup = Integer.MAX_VALUE;
        maxCup = Integer.MIN_VALUE;
        Cup cursor = null;
        firstCup = null;
        for (int index = 0; index < input.length(); index++) {
            if (firstCup == null) {
                firstCup = new Cup();
                firstCup.next = firstCup;
                cursor = firstCup;
            } else {
                Cup newCup = new Cup();
                newCup.next = firstCup;
                cursor.next = newCup;
                cursor = newCup;
            }
            int cup = input.charAt(index) - '0';
            cursor.value = cup;
            mapOfCups.put(cup, cursor);
            maxCup = Utils.max(maxCup, cup);
            minCup = Utils.min(minCup, cup);
        }
        lastCup = cursor;
    }

    private static void doTheRounds(int rounds) {
        Cup curCup = firstCup;
        for (int round = 0; round < rounds; round++) {
            Cup cup1 = curCup.next;
            Cup cup2 = curCup.next.next;
            Cup cup3 = curCup.next.next.next;
            curCup.next = curCup.next.next.next.next;

            int destCupValue = curCup.value;
            do {
                destCupValue = destCupValue - 1;
                if (destCupValue < minCup) {
                    destCupValue = maxCup;
                }
            } while (destCupValue == cup1.value || destCupValue == cup2.value || destCupValue == cup3.value);
            Cup destCup = mapOfCups.get(destCupValue);
            Cup afterDestCup = destCup.next;
            destCup.next = cup1;
            cup3.next = afterDestCup;
            curCup = curCup.next;
        }
    }

    private static String printCupsStartingFrom1() {
        StringBuilder builder = new StringBuilder(100);
        Cup startFrom = mapOfCups.get(1);
        builder.append(startFrom.value);
        Cup cursor = startFrom.next;
        while (cursor != startFrom) {
            builder.append(cursor.value);
            cursor = cursor.next;
        }
        return builder.toString();
    }

}
