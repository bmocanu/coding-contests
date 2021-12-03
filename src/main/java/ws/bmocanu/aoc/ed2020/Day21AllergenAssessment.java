package ws.bmocanu.aoc.ed2020;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day21AllergenAssessment extends SolutionBase {

    private static final Map<String, Integer> ingCountMap = new HashMap<>();
    private static final Map<String, Set<String>> allergMap = new TreeMap<>();

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day21"));
        for (String line : stringLines) {
            List<String> ingList = XUtils.splitCsvStringToStringList(line.substring(0, line.indexOf('(')), " ");
            List<String> allergList = XUtils.splitCsvStringToStringList(line.substring(line.indexOf("(contains") + 9, line.indexOf(')')), ",");
            for (String ing : ingList) {
                ingCountMap.merge(ing, 1, Integer::sum);
            }
            for (String allerg : allergList) {
                Set<String> allergComp = allergMap.get(allerg);
                if (allergComp == null) {
                    allergComp = new HashSet<>();
                    allergComp.addAll(ingList);
                    allergMap.put(allerg, allergComp);
                } else {
                    allergComp.retainAll(ingList);
                }
            }
        }

        int sum = 0;
        for (Map.Entry<String, Integer> ingCountEntry : ingCountMap.entrySet()) {
            boolean isCandidate = true;
            for (Set<String> acmeComp : allergMap.values()) {
                if (acmeComp.contains(ingCountEntry.getKey())) {
                    isCandidate = false;
                    break;
                }
            }
            if (isCandidate) {
                sum += ingCountEntry.getValue();
            }
        }

        Log.part1(sum);

        boolean changePerformed = true;
        while (changePerformed) {
            changePerformed = false;
            for (Map.Entry<String, Set<String>> entry : allergMap.entrySet()) {
                if (entry.getValue().size() == 1) {
                    String ing = entry.getValue().iterator().next();
                    for (Map.Entry<String, Set<String>> otherEntry : allergMap.entrySet()) {
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
        for (Map.Entry<String, Set<String>> entry : allergMap.entrySet()) {
            if (builder.length() > 0) {
                builder.append(',');
            }
            builder.append(entry.getValue().iterator().next());
        }

        Log.part2(builder.toString());
    }

}
