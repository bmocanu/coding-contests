package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day07BagsContainColors extends SolutionBase {

    public static class Content {

        int number;
        String bagColour;

        @Override
        public String toString() {
            return "Content{" +
                   "number=" + number +
                   ", bagColour='" + bagColour + '\'' +
                   '}';
        }
    }

    public static Map<String, List<Content>> bags = new HashMap<>();

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day07"));

        for (String line : stringLines) {
            int firstIndex = line.indexOf(" bags contain ");
            String parent = line.substring(0, firstIndex);
            String rest = line.substring(firstIndex + " bags contain ".length());
            StringTokenizer tokenizer = new StringTokenizer(rest, ",");
            List<Content> parentContent = new ArrayList<>();
            bags.put(parent, parentContent);
            SBind<Content> binder = new SBind("(\\d+) (\\w+ \\w+) bag.*", Content.class, "number", "bagColour");
            while (tokenizer.hasMoreTokens()) {
                String subContent = tokenizer.nextToken().trim();
                if (subContent.contains("no other")) {
                    parentContent.add(new Content());
                } else {
                    parentContent.add(binder.bind(subContent));
                }
            }
        }

        int total = 0;
        for (String rootColour : bags.keySet()) {
            total += solvePart1(rootColour) ? 1 : 0;
        }
        Log.part1(total);
        Log.part2(solvePart2("shiny gold") - 1);
    }

    public static boolean solvePart1(String currentColour) {
        if (bags.get(currentColour).get(0).number == 0) {
            return false;
        }
        boolean localResult = false;
        for (Content content : bags.get(currentColour)) {
            if (content.bagColour.equals("shiny gold")) {
                return true;
            } else {
                localResult = localResult || solvePart1(content.bagColour);
            }
        }
        return localResult;
    }

    public static int solvePart2(String currentColour) {
        if (bags.get(currentColour).get(0).number == 0) {
            return 1;
        }
        int localResult = 0;
        for (Content content : bags.get(currentColour)) {
            localResult = localResult + content.number * solvePart2(content.bagColour);
        }
        return localResult + 1;
    }

}
