package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Interval;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day04CampCleanup extends SolutionBase {

    public static class Pair {
        public Interval i1;
        public Interval i2;
    }

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day04"));
        SBind<Pair> bind = new SBind("(\\d+)-(\\d+),(\\d+)-(\\d+)", Pair.class,
                "i1.left", "i1.right", "i2.left", "i2.right");
        List<Pair> pairList = bind.bind(input);

        int count1 = 0;
        for (Pair pair : pairList) {
            if (pair.i1.contains(pair.i2) || pair.i2.contains(pair.i1)) {
                count1++;
            }
        }
        Log.part1(count1);

        int count2 = 0;
        for (Pair pair : pairList) {
            if (pair.i1.overlaps(pair.i2)) {
                count2++;
            }
        }
        Log.part2(count2);
    }

}
