package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25DoorKeyCryptoBreaking extends SolutionBase {

    public static Map<Long, Integer> cache = new HashMap<>();
    static long div = 20201227;
    static long key1;
    static long key2;

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day25"));
        key1 = Long.parseLong(stringLines.get(0));
        key2 = Long.parseLong(stringLines.get(1));

        int round1 = 0;
        long subjectNr = 7;
        long currentNr = subjectNr;
        while (currentNr != key1) {
            currentNr = currentNr * subjectNr;
            currentNr = currentNr % div;
            if (currentNr < 0) {
                System.out.println("!!!!!!!!");
            }
            round1++;
        }

        subjectNr = key2;
        currentNr = subjectNr;
        for (int roundIdx = 0; roundIdx < round1; roundIdx++) {
            currentNr = currentNr * subjectNr;
            currentNr = currentNr % div;
            if (currentNr < 0) {
                System.out.println("!!!!!!!!");
            }
        }

        Log.part1(currentNr);
    }

}
