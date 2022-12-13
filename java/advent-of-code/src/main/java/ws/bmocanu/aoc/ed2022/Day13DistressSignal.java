package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13DistressSignal extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day13"));

        int part1Sum = 0;
        int pairIndex = 1;

        List<Struct> packets = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < input.size(); ) {
            Struct elem1 = parseLine(input.get(lineIndex));
            packets.add(elem1);
            lineIndex++;
            Struct elem2 = parseLine(input.get(lineIndex));
            packets.add(elem2);
            lineIndex += 2;
            if (compare(elem1, elem2) <= 0) {
                part1Sum += pairIndex;
            }
            pairIndex++;
        }
        Log.part1(part1Sum);

        Struct special1 = parseLine("[[2]]");
        Struct special2 = parseLine("[[6]]");
        packets.add(special1);
        packets.add(special2);
        packets.sort(Day13DistressSignal::compare);
        Log.part2((packets.indexOf(special1) + 1) * (packets.indexOf(special2) + 1));
    }

    private static int compare(Struct s1, Struct s2) {
        if (s1.inners.isEmpty() && s2.inners.isEmpty()) {
            return Integer.compare(s1.value, s2.value);
        } else if (!s1.inners.isEmpty() && !s2.inners.isEmpty()) {
            int index;
            for (index = 0; index < s1.inners.size() && index < s2.inners.size(); index++) {
                int comp = compare(s1.inners.get(index), s2.inners.get(index));
                if (comp != 0) {
                    return comp;
                }
            }
            if (index >= s1.inners.size() && index >= s2.inners.size()) {
                return 0;
            } else if (index >= s1.inners.size()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            if (s1.inners.isEmpty()) {
                if (s1.empty) {
                    return -1;
                } else {
                    return compare(new Struct(Collections.singletonList(new Struct(s1.value))), s2);
                }
            } else {
                if (s2.empty) {
                    return 1;
                } else {
                    return compare(s1, new Struct(Collections.singletonList(new Struct(s2.value))));
                }
            }
        }
    }

    private static Struct parseLine(String line) {
        if (line.indexOf('[') < 0) {
            if (line.isEmpty()) {
                return new Struct(true);
            }
            String[] compList = line.split(",");
            List<Struct> inners = new ArrayList<>();
            for (String comp : compList) {
                inners.add(new Struct(Integer.parseInt(comp)));
            }
            return new Struct(inners);
        }
        int index = 0;
        Struct result = new Struct(0);
        while (index < line.length()) {
            if (line.charAt(index) == '[') {
                int parIndex = index;
                int parDepth = 1;
                while (parDepth > 0) {
                    parIndex++;
                    if (line.charAt(parIndex) == '[') {
                        parDepth++;
                    } else if (line.charAt(parIndex) == ']') {
                        parDepth--;
                    }
                }
                result.inners.add(parseLine(line.substring(index + 1, parIndex)));
                index = parIndex + 1;
            } else if (Character.isDigit(line.charAt(index))) {
                int numIndex = index;
                while (numIndex < line.length() && Character.isDigit(line.charAt(numIndex))) {
                    numIndex++;
                }
                result.inners.add(new Struct(Integer.parseInt(line.substring(index, numIndex))));
                index = numIndex;
            } else {
                index++;
            }
        }
        return result;
    }

    public static class Struct {
        public List<Struct> inners = new ArrayList<>();
        public int value;
        public boolean empty;

        public Struct(List<Struct> inners) {
            this.inners = inners;
        }

        public Struct(int value) {
            this.value = value;
        }

        public Struct(boolean empty) {
            this.empty = empty;
        }
    }

}
