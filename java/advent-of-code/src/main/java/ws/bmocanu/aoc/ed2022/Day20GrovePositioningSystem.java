package ws.bmocanu.aoc.ed2022;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day20GrovePositioningSystem extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day20"));
        for (int part = 1; part <= 2; part++) {
            long numberMultiply = (part == 1 ? 1 : 811589153);
            int mixingRounds = (part == 1 ? 1 : 10);

            List<Number> numbers = new ArrayList<>();
            Number zero = null;
            Number first = null;
            Number last = null;
            for (String line : input) {
                Number nr = new Number(Long.parseLong(line) * numberMultiply);
                numbers.add(nr);
                if (first == null) {
                    first = nr;
                    first.next = first;
                    first.prev = first;
                    last = first;
                } else {
                    nr.next = first;
                    nr.prev = last;
                    last.next = nr;
                    first.prev = nr;
                    last = nr;
                }
                if (nr.value == 0) {
                    zero = nr;
                }
            }

            for (int round = 0; round < mixingRounds; round++) {
                for (Number currentNr : numbers) {
                    if (currentNr.value != 0) {
                        int delta = (currentNr.value > 0 ? 1 : -1);
                        for (long movementIndex = (currentNr.value % (numbers.size() - 1)); movementIndex != 0; movementIndex -= delta) {
                            Number leftNeigh = currentNr.prev;
                            Number rightNeigh = currentNr.next;
                            if (delta < 0) {
                                leftNeigh.prev.next = currentNr;
                                currentNr.prev = leftNeigh.prev;
                                currentNr.next = leftNeigh;
                                leftNeigh.next = rightNeigh;
                                leftNeigh.prev = currentNr;
                                rightNeigh.prev = leftNeigh;
                            } else {
                                rightNeigh.next.prev = currentNr;
                                currentNr.next = rightNeigh.next;
                                currentNr.prev = rightNeigh;
                                rightNeigh.next = currentNr;
                                rightNeigh.prev = leftNeigh;
                                leftNeigh.next = rightNeigh;
                            }
                        }
                    }
                }
            }
            long sum = 0;
            Number cursor = zero;
            for (int index = 1; index <= 3000; index++) {
                cursor = cursor.next;
                if (index % 1000 == 0) {
                    sum += cursor.value;
                }
            }

            Log.part(part, sum);
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private static class Number {
        long value;
        Number next;
        Number prev;

        public Number(long value) {
            this.value = value;
        }
    }

}
