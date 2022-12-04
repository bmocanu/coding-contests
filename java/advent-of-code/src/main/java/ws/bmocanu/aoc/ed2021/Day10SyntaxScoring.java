package ws.bmocanu.aoc.ed2021;

import java.util.*;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day10SyntaxScoring extends SolutionBase {

    static List<Character> openings = Arrays.asList('(', '[', '{', '<');
    static Map<Character, Character> matchMap = new HashMap<>() {{
        put('(', ')');
        put('[', ']');
        put('{', '}');
        put('<', '>');
    }};
    static Map<Character, Integer> pointsMap1 = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};
    static Map<Character, Integer> pointsMap2 = new HashMap<>() {{
        put(')', 1);
        put(']', 2);
        put('}', 3);
        put('>', 4);
    }};

    // 392139
    // 4001832844

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day10"));

        Stack<Character> expMatchStack = new Stack<>();
        int pointsPart1 = 0;
        List<Long> scoresPart2 = new ArrayList<>();
        for (String inputLine : input) {
            char[] expr = inputLine.toCharArray();
            expMatchStack.clear();
            boolean invalidLineFound = false;
            for (char c : expr) {
                if (openings.contains(c)) {
                    expMatchStack.push(matchMap.get(c));
                } else {
                    if (expMatchStack.size() > 0) {
                        char expMatch = expMatchStack.pop();
                        if (expMatch != c) {
                            pointsPart1 += pointsMap1.get(c);
                            invalidLineFound = true;
                            break;
                        }
                    } else {
                        System.out.println("ERROR - Stack is empty. This should never happen with this input");
                    }
                }
            }
            if (!invalidLineFound && expMatchStack.size() > 0) {
                long points = 0;
                while (!expMatchStack.isEmpty()) {
                    points = points * 5 + pointsMap2.get(expMatchStack.pop());
                }
                scoresPart2.add(points);
            }
        }

        Log.part1(pointsPart1);
        Collections.sort(scoresPart2);
        Log.part2(scoresPart2.get(scoresPart2.size() / 2));
    }

}
