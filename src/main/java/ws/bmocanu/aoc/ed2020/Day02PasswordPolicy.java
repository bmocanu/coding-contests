package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day02PasswordPolicy extends SolutionBase {

    public static class Entry {
        int minAp;
        int maxAp;
        char letter;
        String pass;
    }

    public static void main(String[] args) {
        List<String> lineList = XRead.fileAsStringPerLineToStringList(filePath("day02"));
        SBinder parser = new SBinder("(\\d+)-(\\d+) (\\w): (\\w+)",
                                     "minAp", "maxAp", "letter", "pass");
        List<Entry> entries = parser.bind(lineList, Entry.class);

        int validPasswords = 0;
        for (Entry entry : entries) {
            int nrOfAp = 0;
            for (char chr : entry.pass.toCharArray()) {
                if (chr == entry.letter) {
                    nrOfAp++;
                }
            }
            if (nrOfAp >= entry.minAp && nrOfAp <= entry.maxAp) {
                validPasswords++;
            }
        }

        Log.part1(validPasswords);

        validPasswords = 0;
        for (Entry entry : entries) {
            boolean p1Right = false;
            boolean p2Right = false;
            if (entry.minAp - 1 < entry.pass.length()) {
                p1Right = entry.pass.charAt(entry.minAp - 1) == entry.letter;
            }
            if (entry.maxAp - 1 < entry.pass.length()) {
                p2Right = entry.pass.charAt(entry.maxAp - 1) == entry.letter;
            }
            if (p1Right != p2Right) {
                validPasswords++;
            }
        }

        Log.part2(validPasswords);
    }

}
