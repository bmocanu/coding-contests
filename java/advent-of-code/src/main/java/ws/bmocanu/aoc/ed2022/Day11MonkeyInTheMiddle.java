package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.Pointer;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;
import java.util.stream.Collectors;

public class Day11MonkeyInTheMiddle extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day11"));
        List<Monkey> monkeyInput = parseInput(input);

        int monkeyCount = monkeyInput.size();
        int monkeyIndex;
        long[] monkeyInsp = new long[monkeyCount];

        // Part 1
        Arrays.fill(monkeyInsp, 0);
        List<Long>[] monkeyItems = new List[monkeyCount];
        for (monkeyIndex = 0; monkeyIndex < monkeyCount; monkeyIndex++) {
            monkeyItems[monkeyIndex] = new ArrayList<>();
            monkeyItems[monkeyIndex].addAll(
                    monkeyInput.get(monkeyIndex).startingItems.stream().map(Integer::longValue)
                            .collect(Collectors.toList()));
        }

        for (int round = 1; round <= 20; round++) {
            for (monkeyIndex = 0; monkeyIndex < monkeyCount; monkeyIndex++) {
                while (!monkeyItems[monkeyIndex].isEmpty()) {
                    Monkey monkey = monkeyInput.get(monkeyIndex);
                    long item = monkeyItems[monkeyIndex].remove(0);
                    if (monkey.opMultiplyByItself) {
                        item = item * item;
                    } else if (monkey.opMultiply) {
                        item = item * monkey.opValue;
                    } else {
                        item = item + monkey.opValue;
                    }
                    item = item / 3;
                    if (item % monkey.divBy == 0) {
                        monkeyItems[monkey.trueMonkeyIndex].add(item);
                    } else {
                        monkeyItems[monkey.falseMonkeyIndex].add(item);
                    }
                    monkeyInsp[monkeyIndex]++;
                }
            }
        }
        Log.part1(getMonkeyBusinessLevel(monkeyInsp));

        // Part 2
        Arrays.fill(monkeyInsp, 0);
        List<Numb>[] monkeyObj = new List[10];
        for (monkeyIndex = 0; monkeyIndex < monkeyCount; monkeyIndex++) {
            monkeyObj[monkeyIndex] = new ArrayList<>();
            monkeyObj[monkeyIndex].addAll(
                    monkeyInput.get(monkeyIndex).startingItems.stream().map(Numb::new)
                            .collect(Collectors.toList()));
        }

        for (int round = 1; round <= 10000; round++) {
            for (monkeyIndex = 0; monkeyIndex < monkeyCount; monkeyIndex++) {
                while (!monkeyObj[monkeyIndex].isEmpty()) {
                    Monkey monkey = monkeyInput.get(monkeyIndex);
                    Numb obj = monkeyObj[monkeyIndex].remove(0);
                    if (monkey.opMultiplyByItself) {
                        obj.squareYourself();
                    } else if (monkey.opMultiply) {
                        obj.multiplyBy(monkey.opValue);
                    } else {
                        obj.add(monkey.opValue);
                    }
                    if (obj.isDivisibleBy(monkey.divBy)) {
                        monkeyObj[monkey.trueMonkeyIndex].add(obj);
                    } else {
                        monkeyObj[monkey.falseMonkeyIndex].add(obj);
                    }
                    monkeyInsp[monkeyIndex]++;
                }
            }
        }

        Log.part2(getMonkeyBusinessLevel(monkeyInsp));
    }

    // ----------------------------------------------------------------------------------------------------

    @SuppressWarnings("DataFlowIssue")
    private static List<Monkey> parseInput(List<String> input) {
        List<Monkey> monkeyInput = new ArrayList<>();
        Monkey monkey = null;
        for (String line : input) {
            line = line.trim();
            if (line.startsWith("Monkey")) {
                monkey = new Monkey();
                monkeyInput.add(monkey);
            } else if (line.startsWith("Starting")) {
                String[] comp = line.split(" ");
                for (int index = 2; index < comp.length; index++) {
                    monkey.startingItems.add(Integer.valueOf(comp[index].split(",")[0]));
                }
            } else if (line.startsWith("Operation:")) {
                String[] comp = line.split(" ");
                if (comp[4].equals("*")) {
                    if (comp[5].equals("old")) {
                        monkey.opMultiplyByItself = true;
                    } else {
                        monkey.opMultiply = true;
                        monkey.opValue = Integer.parseInt(comp[5]);
                    }
                } else {
                    monkey.opAdd = true;
                    monkey.opValue = Integer.parseInt(comp[5]);
                }
            } else if (line.startsWith("Test:")) {
                String[] comp = line.split(" ");
                monkey.divBy = Integer.parseInt(comp[3]);
            } else if (line.startsWith("If true:")) {
                String[] comp = line.split(" ");
                monkey.trueMonkeyIndex = Integer.parseInt(comp[5]);
            } else if (line.startsWith("If false:")) {
                String[] comp = line.split(" ");
                monkey.falseMonkeyIndex = Integer.parseInt(comp[5]);
            }
        }
        return monkeyInput;
    }

    @SuppressWarnings("unchecked")
    private static long getMonkeyBusinessLevel(long[] monkeyInsp) {
        Pointer<Long>[] monkeyMaxes = (Pointer<Long>[]) XUtils.multiMaxFromArray(monkeyInsp, 2);
        return monkeyMaxes[0].value * monkeyMaxes[1].value;
    }

    // ----------------------------------------------------------------------------------------------------

    private static class Monkey {
        List<Integer> startingItems = new ArrayList<>();
        boolean opMultiplyByItself;
        boolean opMultiply;
        boolean opAdd;
        int opValue;
        int divBy;
        int trueMonkeyIndex;
        int falseMonkeyIndex;
    }

    private static final int[] dividers = new int[]{2, 3, 5, 7, 11, 13, 17, 19};

    private static class Numb {

        private final Map<Integer, Integer> modulos = new HashMap<>();

        public Numb(int nr) {
            for (int divider : dividers) {
                modulos.put(divider, 0);
            }
            add(nr);
        }

        public void add(int value) {
            for (int divider : dividers) {
                int currentModulo = modulos.get(divider);
                modulos.put(divider, (currentModulo + value) % divider);
            }
        }

        public void multiplyBy(int value) {
            for (int divider : dividers) {
                int currentModulo = modulos.get(divider);
                modulos.put(divider, (currentModulo * value) % divider);
            }
        }

        public void squareYourself() {
            for (int divider : dividers) {
                int currentModulo = modulos.get(divider);
                modulos.put(divider, (currentModulo * currentModulo) % divider);
            }
        }

        public boolean isDivisibleBy(int divider) {
            return modulos.get(divider) == 0;
        }

    }
}
