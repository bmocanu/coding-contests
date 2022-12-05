package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.*;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.LinkedList;
import java.util.List;

public class Day22ReactorRebootCubes extends SolutionBase {

    public static void main(String[] args) {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day22"));
        SBind<CubeSection> binder = new SBind<>("([a-z]+) x=([-0-9]+)\\.\\.([-0-9]+),y=([-0-9]+)\\.\\.([-0-9]+),z=([-0-9]+)\\.\\.([-0-9]+)",
                CubeSection.class, "stateStr", "x1", "x2", "y1", "y2", "z1", "z2");
        List<CubeSection> inputSections = binder.bindAll(inputLines);

        for (int part = 1; part <= 2; part++) {
            List<CubeSection> knownSections = new LinkedList<>();
            for (CubeSection newSec : inputSections) {
                newSec.state = newSec.stateStr.equals("on");
                if (part == 2 || (newSec.x1 >= -50 && newSec.x2 <= 50)) {
                    List<CubeSection> newlyAddedSections = new LinkedList<>();
                    if (newSec.state) {
                        // this is a new section, add it to the list
                        newlyAddedSections.add(newSec);
                    }
                    for (CubeSection knownSec : knownSections) {
                        if (sectionsOverlap(knownSec, newSec)) {
                            CubeSection commonSec = getIntersectionOf(knownSec, newSec);
                            commonSec.state = !knownSec.state;
                            newlyAddedSections.add(commonSec);
                        }
                    }
                    knownSections.addAll(newlyAddedSections);
                }
            }

            long count = 0;
            for (CubeSection sec : knownSections) {
                count += (sec.state ? 1 : -1) * sec.volume();
            }

            if (part == 1) {
                Log.part1(count);
            } else {
                Log.part2(count);
            }
        }
    }

    static CubeSection getIntersectionOf(CubeSection s1, CubeSection s2) {
        //   1 2 3 4 5 6 7 8 9
        // 1
        // 2 +-+-+-+-+
        // 3 |       |
        // 4 |   +-+-+-+-+
        // 5 |   |   |   |
        // 6 +-+-+-+-+   |
        // 7     |       |
        // 8     +-+-+-+-+
        // 9
        //
        //   1 2 3 4 5 6 7 8 9
        // 1
        // 2 +-+-+-+-+-+-+-+
        // 3 |             |
        // 4 |   +-+-+-+   |
        // 5 |   |     |   |
        // 6 +-+-+-+-+-+-+-+
        // 7     |     |
        // 8     +-+-+-+
        // 9
        //
        //   1 2 3 4 5 6 7 8 9
        // 1
        // 2 +-+-+-+-+
        // 3 |       |
        // 4 |       |   +-+-+
        // 5 |       |   |   |
        // 6 +-+-+-+-+   |   |
        // 7             |   |
        // 8             +-+-+
        // 9
        CubeSection result = new CubeSection();
        result.x1 = Math.max(s1.x1, s2.x1);
        result.x2 = Math.min(s1.x2, s2.x2);
        result.y1 = Math.max(s1.y1, s2.y1);
        result.y2 = Math.min(s1.y2, s2.y2);
        result.z1 = Math.max(s1.z1, s2.z1);
        result.z2 = Math.min(s1.z2, s2.z2);
        return result;
    }

    static boolean sectionsOverlap(CubeSection s1, CubeSection s2) {
        return Math.max(s1.x1, s2.x1) <= Math.min(s1.x2, s2.x2) &&
                Math.max(s1.y1, s2.y1) <= Math.min(s1.y2, s2.y2) &&
                Math.max(s1.z1, s2.z1) <= Math.min(s1.z2, s2.z2);
    }

    // ----------------------------------------------------------------------------------------------------

    public static class CubeSection {
        int x1;
        int x2;
        int y1;
        int y2;
        int z1;
        int z2;
        boolean state;
        String stateStr;

        long volume() {
            return ((long) x2 - x1 + 1) *
                    ((long) y2 - y1 + 1) *
                    ((long) z2 - z1 + 1);
        }
    }

}
