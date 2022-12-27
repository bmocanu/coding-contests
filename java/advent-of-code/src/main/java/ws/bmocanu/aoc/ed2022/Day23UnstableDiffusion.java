package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day23UnstableDiffusion extends SolutionBase {

    private static final List<Point> elves = new ArrayList<>();
    private static final Set<String> elvesPos = new HashSet<>();

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day23"));
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    Point newPoint = Point.from(x, y);
                    elves.add(newPoint);
                    elvesPos.add(serialize(newPoint));
                }
            }
        }

        List<DeltaMove> deltaMoves = getDeltaMoves();
        for (int round = 1; ; round++) {
            boolean atLeastOneElfMoved = false;
            for (Point elf : elves) {
                boolean elfNeedsToMove = false;
                for (PosDelta8 posDelta : PosDelta8.deltaValues) {
                    if (thereIsElfAt(elf.getOtherPointByDelta(posDelta))) {
                        elfNeedsToMove = true;
                        break;
                    }
                }

                if (elfNeedsToMove) {
                    for (DeltaMove deltaMove : deltaMoves) {
                        if (!thereIsElfAt(elf.getOtherPointByDelta(deltaMove.positions[0])) &&
                                !thereIsElfAt(elf.getOtherPointByDelta(deltaMove.positions[1])) &&
                                !thereIsElfAt(elf.getOtherPointByDelta(deltaMove.positions[2]))) {
                            Point newPos = elf.getOtherPointByDelta(deltaMove.positions[1]);
                            elf.newX = newPos.x;
                            elf.newY = newPos.y;
                            elf.mark();
                            break;
                        }
                    }
                    if (!elf.marked) {
                        elf.newX = elf.x;
                        elf.newY = elf.y;
                    }
                }
            }

            for (int elfI1 = 0; elfI1 < elves.size() - 1; elfI1++) {
                Point elf1 = elves.get(elfI1);
                if (elf1.marked) {
                    for (int elfI2 = elfI1 + 1; elfI2 < elves.size(); elfI2++) {
                        Point elf2 = elves.get(elfI2);
                        if (elf2.marked && elf1.newX == elf2.newX && elf1.newY == elf2.newY) {
                            elf1.unmark();
                            elf2.unmark();
                            break;
                        }
                    }
                }
            }

            for (Point elf : elves) {
                if (elf.marked) {
                    elvesPos.remove(serialize(elf));
                    elf.x = elf.newX;
                    elf.y = elf.newY;
                    elvesPos.add(serialize(elf));
                    elf.unmark();
                    atLeastOneElfMoved = true;
                }
            }
            deltaMoves.add(deltaMoves.remove(0));
            int spaceContent = calculateRectangleSpaceContent();
            System.out.println("Round = " + round + ", spaceContent=" + spaceContent + ", bool=" + atLeastOneElfMoved);

            if (round == 10) {
                Log.part1(calculateRectangleSpaceContent());
            }
            if (!atLeastOneElfMoved) {
                Log.part2(round);
                break;
            }
        }
    }

    private static boolean thereIsElfAt(Point point) {
        return elvesPos.contains(serialize(point));
    }

    private static List<DeltaMove> getDeltaMoves() {
        List<DeltaMove> moves = new ArrayList<>();
        moves.add(new DeltaMove(new PosDelta8[]{PosDelta8.upLeft(), PosDelta8.up(), PosDelta8.upRight()}));
        moves.add(new DeltaMove(new PosDelta8[]{PosDelta8.downLeft(), PosDelta8.down(), PosDelta8.downRight()}));
        moves.add(new DeltaMove(new PosDelta8[]{PosDelta8.upLeft(), PosDelta8.left(), PosDelta8.downLeft()}));
        moves.add(new DeltaMove(new PosDelta8[]{PosDelta8.upRight(), PosDelta8.right(), PosDelta8.downRight()}));
        return moves;
    }

    private static int calculateRectangleSpaceContent() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point elf : elves) {
            if (elf.x < minX) {
                minX = elf.x;
            }
            if (elf.x > maxX) {
                maxX = elf.x;
            }
            if (elf.y < minY) {
                minY = elf.y;
            }
            if (elf.y > maxY) {
                maxY = elf.y;
            }
        }

        return (maxX - minX + 1) * (maxY - minY + 1) - elves.size();
    }

    private static String serialize(Point point) {
        return point.x + "," + point.y;
    }

    private static class DeltaMove {
        PosDelta8[] positions;

        public DeltaMove(PosDelta8[] positions) {
            this.positions = positions;
        }
    }

}
