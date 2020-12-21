package ws.bmocanu.aoc.ed2020;

import java.util.*;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day21 extends SolutionBase {

    private static class Allergen {
        String name;
        int count;
    }

    private static class Stats {
        Map<String, Allergen> allergMap = new HashMap<>();
        List<String> allergList = new ArrayList<>();
        int count;
    }

    private static class Input {
        List<String> ingList = new ArrayList<>();
        List<String> allergList = new ArrayList<>();
    }


    private static Map<String, Stats> statsMap = new HashMap<>();

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day21"));
        List<Input> inputList = new ArrayList<>();
        for (String line : stringLines) {
            Input input = new Input();
            inputList.add(input);
            String ingStr = line.substring(0, line.indexOf('(')).trim();
            StringTokenizer tokenizer = new StringTokenizer(ingStr, " ");
            while (tokenizer.hasMoreTokens()) {
                input.ingList.add(tokenizer.nextToken());
            }
            String allergStr = line.substring(line.indexOf("(contains") + 9, line.indexOf(')')).trim();
            tokenizer = new StringTokenizer(allergStr, ",");
            while (tokenizer.hasMoreTokens()) {
                input.allergList.add(tokenizer.nextToken().trim());
            }
            for (String ing : input.ingList) {
                Stats stats = statsMap.get(ing);
                if (stats == null) {
                    stats = new Stats();
                    statsMap.put(ing, stats);
                }
                stats.count++;
                for (String allerg : input.allergList) {
                    Allergen allergObj = stats.allergMap.get(allerg);
                    if (allergObj == null) {
                        allergObj = new Allergen();
                        allergObj.name = allerg;
                        stats.allergMap.put(allerg, allergObj);
                    }
                    allergObj.count++;
                }
            }
        }

        Map<String, Set<String>> acme = new TreeMap<>();

        for (Input input : inputList) {
            for (String allerg : input.allergList) {
                Set<String> allergComp = acme.get(allerg);
                if (allergComp == null) {
                    allergComp = new HashSet<>();
                    allergComp.addAll(input.ingList);
                    acme.put(allerg, allergComp);
                } else {
                    allergComp.retainAll(input.ingList);
                }
            }
        }

        int sum = 0;
        for (String ingName : statsMap.keySet()) {
            boolean isCandidate = true;
            for (Set<String> acmeComp : acme.values()) {
                if (acmeComp.contains(ingName)) {
                    isCandidate = false;
                }
            }
            if (isCandidate) {
                sum += statsMap.get(ingName).count;
            }
        }

        Log.part1(sum);

        boolean changePerformed = true;
        while (changePerformed) {
            changePerformed = false;
            for (Map.Entry<String, Set<String>> entry : acme.entrySet()) {
                if (entry.getValue().size() == 1) {
                    String ing = entry.getValue().iterator().next();
                    for (Map.Entry<String, Set<String>> otherEntry : acme.entrySet()) {
                        if (!otherEntry.getKey().equals(entry.getKey())) {
                            if (otherEntry.getValue().contains(ing)) {
                                otherEntry.getValue().remove(ing);
                                changePerformed = true;
                            }
                        }
                    }
                }
            }
        }

        StringBuilder builder = new StringBuilder(300);
        for (Map.Entry<String, Set<String>> entry : acme.entrySet()) {
            builder.append(entry.getValue().iterator().next()).append(',');
        }

        Log.part2(builder.toString());
    }

}
