package ws.bmocanu.aoc.ed2020;

import java.util.*;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day19 extends SolutionBase {

    public static class Rule {
        int id;
        List<Integer> or1;
        List<Integer> or2;
        Character chrToMatch;
        int considered = 0;

        public Rule(int id, List<Integer> or1, List<Integer> or2, Character chrToMatch) {
            this.id = id;
            this.or1 = or1;
            this.or2 = or2;
            this.chrToMatch = chrToMatch;
        }

        public Rule() {
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "id=" + id +
                    ", or1=" + or1 +
                    ", or2=" + or2 +
                    ", chrToMatch=" + chrToMatch +
                    '}';
        }
    }

    private static Map<Integer, Rule> ruleMap;
    private static List<String> combinations;

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day19"));
        ruleMap = new HashMap<>();
        int index = 0;
        String line;
        SBinder charBinder = new SBinder("(\\d+): \"(\\w+)\"", "id", "chrToMatch");
        SBinder orBinder = new SBinder("(\\d+): \"(\\w+)\"", "id", "chrToMatch");
        while (!(line = stringLines.get(index)).isBlank()) {
            Rule newRule = null;
            if (line.contains("\"")) {
                newRule = charBinder.bind(line, Rule.class);
            } else if (line.contains("|")) {
                newRule = new Rule();
                newRule.id = Integer.parseInt(line.substring(0, line.indexOf(':')));
                String or1 = line.substring(line.indexOf(':') + 1, line.indexOf('|'));
                String or2 = line.substring(line.indexOf('|') + 1);
                StringTokenizer tokenizer = new StringTokenizer(or1.trim(), " ");
                newRule.or1 = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    newRule.or1.add(Integer.parseInt(tokenizer.nextToken()));
                }
                tokenizer = new StringTokenizer(or2.trim(), " ");
                newRule.or2 = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    newRule.or2.add(Integer.parseInt(tokenizer.nextToken()));
                }
            } else {
                newRule = new Rule();
                newRule.id = Integer.parseInt(line.substring(0, line.indexOf(':')));
                String or1 = line.substring(line.indexOf(':') + 1);
                StringTokenizer tokenizer = new StringTokenizer(or1.trim(), " ");
                newRule.or1 = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    newRule.or1.add(Integer.parseInt(tokenizer.nextToken()));
                }
            }
            ruleMap.put(newRule.id, newRule);
            index++;
        }

        ruleMap.put(8, new Rule(8, Arrays.asList(new Integer[]{42}), Arrays.asList(new Integer[]{42, 8}), null));
        ruleMap.put(11, new Rule(11, Arrays.asList(new Integer[]{42, 31}), Arrays.asList(new Integer[]{42, 11, 31}), null));

        List<String> allCombs = generateCombinations(0, true, 0);
        List<String> input = new ArrayList<>();

        index++;
        while (index < stringLines.size()) {
            input.add(stringLines.get(index));
            index++;
        }

        int matchCount = 0;
        for (String inputLine : input) {
            if (allCombs.contains(inputLine)) {
                matchCount++;
            }
        }

        Log.part1(matchCount);

        // 8: 42 | 42 8
        //11: 42 31 | 42 11 31
//        ruleMap.put(8, new Rule(8, Arrays.asList(new Integer[]{42}), Arrays.asList(new Integer[]{42, 8}), null));
//        ruleMap.put(11, new Rule(11, Arrays.asList(new Integer[]{42, 31}), Arrays.asList(new Integer[]{42, 11, 31}), null));

//        allCombs = generateCombinations(0, true, 0);
//        matchCount = 0;
//        for (String inputLine : input) {
//            if (allCombs.contains(inputLine)) {
//                matchCount++;
//            }
//        }
//
//        Log.part2(matchCount);
    }

    private static int LIMIT = 1;

    private static List<String> generateCombinations(int id, boolean withLoops, int depth) {
        Log.info(depth, "id=", id);
        Rule idRule = ruleMap.get(id);
        List<String> result = new ArrayList<>();
        if (withLoops && (id == 8 || id == 11) && idRule.considered > LIMIT) {
            result.add("");
            return result;
        }
        idRule.considered++;
        if (idRule.chrToMatch != null) {
            result.add(idRule.chrToMatch + "");
        } else if (idRule.or2 == null) {
            List<List<String>> combs = new ArrayList<>();
            for (Integer elem : idRule.or1) {
//                if (!withLoops || depth < LIMIT) {
                combs.add(generateCombinations(elem, withLoops, depth + 1));
//                } else {
//                    combs.add(new HashList<>(Arrays.asList("")));
//                }
            }
            generateSubCombs(combs, 0, "", result);
        } else {
            List<List<String>> combs = new ArrayList<>();
            for (Integer elem : idRule.or1) {
//                if (!withLoops || depth < LIMIT) {
                combs.add(generateCombinations(elem, withLoops, depth + 1));
//                } else {
//                    combs.add(new HashList<>(Arrays.asList("")));
//                }
            }
            generateSubCombs(combs, 0, "", result);
            combs = new ArrayList<>();
            for (Integer elem : idRule.or2) {
//                if (!withLoops || depth < LIMIT) {
                combs.add(generateCombinations(elem, withLoops, depth + 1));
//                } else {
//                    combs.add(new HashList<>(Arrays.asList("")));
//                }
            }
            generateSubCombs(combs, 0, "", result);
        }
        return result;
    }

    private static void generateSubCombs(List<List<String>> combs, int combIndex, String partial, List<String> result) {
        if (combIndex >= combs.size() || partial.length() > 90) {
            result.add(partial);
        } else {
            for (String comb : combs.get(combIndex)) {
                generateSubCombs(combs, combIndex + 1, partial + comb, result);
            }
        }
    }

}
