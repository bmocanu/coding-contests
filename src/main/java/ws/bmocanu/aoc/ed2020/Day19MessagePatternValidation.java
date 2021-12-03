package ws.bmocanu.aoc.ed2020;

import java.util.*;
import java.util.regex.Pattern;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day19MessagePatternValidation extends SolutionBase {

    private static Map<Integer, Rule> ruleMap;

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day19"));
        ruleMap = new HashMap<>();
        int index = 0;
        String line;
        SBind charBinder = new SBind("(\\d+): \"(\\w+)\"", "id", "chrToMatch");
        while (!(line = stringLines.get(index)).isBlank()) {
            Rule newRule;
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

        List<String> inputLines = new ArrayList<>();
        index++;
        while (index < stringLines.size()) {
            inputLines.add(stringLines.get(index));
            index++;
        }

        // Part 1
        Log.part1(countMatches(generateRegexExp(0, 0, false), inputLines));

        // Part 2
        ruleMap.put(8, new Rule(8, Arrays.asList(42), Arrays.asList(42, 8), null));
        ruleMap.put(11, new Rule(11, Arrays.asList(42, 31), Arrays.asList(42, 11, 31), null));

        Log.part2(countMatches(generateRegexExp(0, 0, true), inputLines));
    }

    private static String generateRegexExp(int id, int depth, boolean withLoops) {
        Rule idRule = ruleMap.get(id);
        if (withLoops) {
            if (id == 8) {
                return generateRegexExp(42, depth, withLoops) + "+";
            }
            if (id == 11 && depth > 5) {
                return generateRegexExp(42, depth + 1, withLoops) +
                        generateRegexExp(31, depth + 1, withLoops);
            }
        }
        if (idRule.chrToMatch != null) {
            return idRule.chrToMatch + "";
        }
        StringBuilder builder = new StringBuilder(30);
        if (idRule.or2 == null) {
            for (Integer elem : idRule.or1) {
                builder.append(generateRegexExp(elem, depth + 1, withLoops));
            }
        } else {
            builder.append('(');
            for (Integer elem : idRule.or1) {
                builder.append(generateRegexExp(elem, depth + 1, withLoops));
            }
            builder.append('|');
            for (Integer elem : idRule.or2) {
                builder.append(generateRegexExp(elem, depth + 1, withLoops));
            }
            builder.append(')');
        }
        return builder.toString();
    }

    private static int countMatches(String regexp, List<String> inputLines) {
        Pattern pattern = Pattern.compile(regexp);
        int matchCount = 0;
        for (String inputLine : inputLines) {
            if (pattern.matcher(inputLine).matches()) {
                matchCount++;
            }
        }
        return matchCount;
    }

    // ----------------------------------------------------------------------------------------------------

    public static class Rule {
        int id;
        List<Integer> or1;
        List<Integer> or2;
        Character chrToMatch;

        public Rule(int id, List<Integer> or1, List<Integer> or2, Character chrToMatch) {
            this.id = id;
            this.or1 = or1;
            this.or2 = or2;
            this.chrToMatch = chrToMatch;
        }

        public Rule() {
        }
    }

}
