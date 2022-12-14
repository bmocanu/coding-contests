package ws.bmocanu.aoc.ed2022;

import java.util.List;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day09RopeBridge extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day09"));
        FlexStruct flex = new FlexStruct();

        for (int part = 1; part <= 2; part++) {
            int LENGTH = (part == 1 ? 2 : 10);
            int HEAD = 0;
            int TAIL = (part == 1 ? 1 : 9);
            flex.forAllPoints().unmark();

            Point[] rope = new Point[LENGTH];
            for (int index = 0; index < LENGTH; index++) {
                rope[index] = flex.point(1000, 1000);
            }

            for (String line : input) {
                String[] comp = line.split(" ");
                PosDelta4 headDelta = PosDelta4.fromStrings(comp[0], "U", "D", "L", "R");
                int increment = Integer.parseInt(comp[1]);
                for (int x = 0; x < increment; x++) {
                    rope[HEAD] = flex.point(rope[HEAD], headDelta);
                    for (int index = 1; index < LENGTH; index++) {
                        if (linkNeedsToMove(rope[index - 1], rope[index])) {
                            if (rope[index - 1].x < rope[index].x) {
                                rope[index] = flex.point(rope[index], PosDelta4.west());
                            } else if (rope[index - 1].x > rope[index].x) {
                                rope[index] = flex.point(rope[index], PosDelta4.east());
                            }
                            if (rope[index - 1].y < rope[index].y) {
                                rope[index] = flex.point(rope[index], PosDelta4.north());
                            } else if (rope[index - 1].y > rope[index].y) {
                                rope[index] = flex.point(rope[index], PosDelta4.south());
                            }
                        }
                    }
                    rope[TAIL].mark();
                }
            }

            Log.part(part, flex.countPointsMarked());
        }
    }

    private static boolean linkNeedsToMove(Point head, Point tail) {
        return (Math.abs(head.x - tail.x) > 1) ||
               (Math.abs(head.y - tail.y) > 1);
    }

}
