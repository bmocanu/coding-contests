package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day25SeaCucumber extends SolutionBase {

    static FlexStruct flex;
    static int flexWidth;
    static int flexHeight;

    public static void main(String[] args) {
        flex = FlexStruct.fromFile(filePath("day25"));
        flexWidth = flex.width();
        flexHeight = flex.height();

        boolean oneSCMoved = true;
        int step = 0;
        while (oneSCMoved) {
            step++;
            oneSCMoved = false;
            for (Point p : flex.allPointsOfChar('>')) {
                Point nextP = flex.pointOrNull(p.x + 1, p.y);
                if (nextP == null) {
                    nextP = flex.pointOrNull(0, p.y);
                }
                if (nextP.chr == '.') {
                    p.mark();
                }
            }
            for (Point p : flex.allPointsMarked()) {
                Point nextP = flex.pointOrNull(p.x + 1, p.y);
                if (nextP == null) {
                    nextP = flex.pointOrNull(0, p.y);
                }
                p.marked = false;
                p.chr = '.';
                nextP.chr = '>';
                oneSCMoved = true;
            }
            for (Point p : flex.allPointsOfChar('v')) {
                Point nextP = flex.pointOrNull(p.x, p.y + 1);
                if (nextP == null) {
                    nextP = flex.pointOrNull(p.x, 0);
                }
                if (nextP.chr == '.') {
                    p.mark();
                }
            }
            for (Point p : flex.allPointsMarked()) {
                Point nextP = flex.pointOrNull(p.x, p.y + 1);
                if (nextP == null) {
                    nextP = flex.pointOrNull(p.x, 0);
                }
                p.marked = false;
                p.chr = '.';
                nextP.chr = 'v';
                oneSCMoved = true;
            }
        }

        Log.part1(step);
    }

}
