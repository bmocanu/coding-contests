package ws.bmocanu.aoc.ed2021;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SMap;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day14ExtendedPolymerization extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day14"));
        char[] startChars = input.get(0).toCharArray();
        SMap<String, Character> modifs = new SMap<>();
        for (int index = 2; index < input.size(); index++) {
            String[] lineParts = input.get(index).split(" -> ");
            modifs.put(lineParts[0], lineParts[1].charAt(0));
        }

        SMap<String, Long> pairs = new SMap<>();
        SMap<Character, AtomicLong> counts = new SMap<>();
        for (int index = 0; index < startChars.length - 1; index++) {
            String elem = "" + startChars[index] + startChars[index + 1];
            pairs.put(elem, pairs.getSafely(elem, 0L) + 1);
            counts.getSafely(startChars[index], new AtomicLong(0)).incrementAndGet();
        }
        counts.getSafely(startChars[startChars.length - 1], new AtomicLong(0)).incrementAndGet();

        for (int step = 0; step < 40; step++) {
            SMap<String, Long> newPairs = new SMap<>();
            for (String pair : pairs.keySet()) {
                long pairCount = pairs.get(pair);
                String newElem = "" + pair.charAt(0) + modifs.get(pair);
                newPairs.put(newElem, newPairs.getSafely(newElem, 0L) + pairCount);
                newElem = "" + modifs.get(pair) + pair.charAt(1);
                newPairs.put(newElem, newPairs.getSafely(newElem, 0L) + pairCount);
                counts.getSafely(modifs.get(pair), new AtomicLong(0)).addAndGet(pairCount);
            }
            pairs = newPairs;

            if (step == 9) {
                Log.part1(XUtils.maxFromCollection(counts.values()) - XUtils.minFromCollection(counts.values()));
            }
            if (step == 39) {
                Log.part2(XUtils.maxFromCollection(counts.values()) - XUtils.minFromCollection(counts.values()));
            }
        }
    }

}
