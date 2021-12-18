package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day18SnailfishMath extends SolutionBase {

    public static void main(String[] args) {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day18"));
        List<Pair> inputPairs = inputLines
                .stream()
                .map(line -> reduce(parse(line, 0)))
                .collect(Collectors.toList());
        Pair part1Sum = inputPairs
                .stream()
                .reduce((p1, p2) -> sum(p1, p2))
                .get();
        Log.part1(part1Sum.getMagnitude());

        int maxMag = Integer.MIN_VALUE;
        for (Pair p1 : inputPairs) {
            for (Pair p2 : inputPairs) {
                if (p1 != p2) {
                    int sumMag = sum(p1, p2).getMagnitude();
                    if (sumMag > maxMag) {
                        maxMag = sumMag;
                    }
                }
            }
        }
        Log.part2(maxMag);
    }

    // ----------------------------------------------------------------------------------------------------

    public static Pair sum(Pair p1, Pair p2) {
        Pair sum = new Pair();
        sum.left = p1.copy();
        sum.right = p2.copy();
        sum.depth = 0;
        sum.incDepth();
        return reduce(sum);
    }

    public static Pair reduce(Pair inputPair) {
        Pair pair = inputPair.copy();
        List<Pair> allPairs = new LinkedList<>();
        List<Pair> valuePairs = new LinkedList<>();
        boolean reductionDone = true;
        while (reductionDone) {
            reductionDone = false;
            allPairs.clear();
            valuePairs.clear();
            pair.addToList(allPairs);
            pair.addToListJustTheValues(valuePairs);
            for (Pair p : allPairs) {
                if (p.depth > 4 && !p.justAValue && p.left.justAValue && p.right.justAValue) {
                    int leftValueIndex = valuePairs.indexOf(p.left);
                    if (leftValueIndex > 0) {
                        valuePairs.get(leftValueIndex - 1).value += p.left.value;
                    }
                    int rightValueIndex = valuePairs.indexOf(p.right);
                    if (rightValueIndex < valuePairs.size() - 1) {
                        valuePairs.get(rightValueIndex + 1).value += p.right.value;
                    }
                    p.value = 0;
                    p.justAValue = true;
                    p.left = null;
                    p.right = null;
                    p.depth--;
                    reductionDone = true;
                    break;
                }
            }
            if (!reductionDone) {
                for (Pair p : valuePairs) {
                    if (p.value >= 10) {
                        p.depth++;
                        p.left = new Pair();
                        p.left.value = p.value / 2;
                        p.left.justAValue = true;
                        p.left.depth = p.depth;
                        p.right = new Pair();
                        p.right.value = p.value / 2 + p.value % 2;
                        p.right.justAValue = true;
                        p.right.depth = p.depth;
                        p.value = 0;
                        p.justAValue = false;
                        reductionDone = true;
                        break;
                    }
                }
            }
        }
        return pair;
    }

    public static Pair parse(String input, int pairDepth) {
        Pair result = new Pair();
        result.depth = pairDepth;
        if (!input.contains(",")) {
            result.value = Integer.parseInt(input);
            result.justAValue = true;
            return result;
        }
        int depth = 0;
        for (int index = 0; index < input.length(); index++) {
            if (input.charAt(index) == '[') {
                depth++;
            }
            if (input.charAt(index) == ']') {
                depth--;
            }
            if (input.charAt(index) == ',' && depth == 0) {
                result.left = parse(input.substring(0, index), pairDepth);
                result.right = parse(input.substring(index + 1), pairDepth);
                return result;
            }
        }
        return parse(input.substring(1, input.length() - 1), pairDepth + 1);
    }

    // ----------------------------------------------------------------------------------------------------

    static class Pair {
        int value;
        int depth;
        Pair left;
        Pair right;
        boolean justAValue;

        public Pair copy() {
            Pair newPair = new Pair();
            newPair.value = value;
            newPair.justAValue = justAValue;
            newPair.depth = depth;
            if (left != null) {
                newPair.left = left.copy();
            }
            if (right != null) {
                newPair.right = right.copy();
            }
            return newPair;
        }

        public void addToList(List<Pair> list) {
            list.add(this);
            if (!justAValue) {
                left.addToList(list);
                right.addToList(list);
            }
        }

        public void addToListJustTheValues(List<Pair> list) {
            if (justAValue) {
                list.add(this);
            } else {
                left.addToListJustTheValues(list);
                right.addToListJustTheValues(list);
            }
        }

        public int getMagnitude() {
            if (justAValue) {
                return value;
            } else {
                return left.getMagnitude() * 3 + right.getMagnitude() * 2;
            }
        }

        public void incDepth() {
            depth++;
            if (!justAValue) {
                left.incDepth();
                right.incDepth();
            }
        }
    }

}
