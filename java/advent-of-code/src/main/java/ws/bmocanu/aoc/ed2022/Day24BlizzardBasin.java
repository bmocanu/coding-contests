package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.utils.SList;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day24BlizzardBasin extends SolutionBase {

    private static final List<Blizzard> blizzardList = new LinkedList<>();
    private static int WIDTH;
    private static int HEIGHT;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day24"));
        HEIGHT = input.size();
        WIDTH = input.get(0).length();
        parseInputToBlizzardList(input);

        int startX = 1;
        int startY = 0;
        int endX = WIDTH - 2;
        int endY = HEIGHT - 1;

        int startToEnd = calculateShortestPath(startX, startY, endX, endY, 0);
        Log.part1(startToEnd);

        int endToStart = calculateShortestPath(endX, endY, startX, startY, startToEnd);
        int againStartToEnd = calculateShortestPath(startX, startY, endX, endY, endToStart);
        Log.part2(againStartToEnd);
    }

    // ----------------------------------------------------------------------------------------------------

    private static final Move[] MOVES = {
            new Move(0, -1),
            new Move(0, 1),
            new Move(-1, 0),
            new Move(1, 0),
            new Move(0, 0)};

    private static int calculateShortestPath(int startX, int startY, int endX, int endY, int startMinute) {
        Node startNode = new Node(startX, startY, startMinute);
        SList<Node> queue = new SList<>();
        queue.add(startNode);
        Set<Node> visited = new HashSet<>();
        visited.add(startNode);
        while (!queue.isEmpty()) {
            Node currentNode = queue.popFirst();
            for (Move move : MOVES) {
                Node newNode = new Node(
                        currentNode.x + move.deltaX,
                        currentNode.y + move.deltaY,
                        currentNode.minute + 1);
                if (newNode.x == endX && newNode.y == endY) {
                    return newNode.minute;
                }
                if (!visited.contains(newNode) &&
                        posIsBetweenBoundaries(newNode.x, newNode.y) &&
                        posIsOutsideOfBlizzards(newNode.x, newNode.y, newNode.minute)) {
                    visited.add(newNode);
                    queue.add(newNode);
                }
            }

        }
        throw new IllegalStateException("Invalid algo state");
    }

    private static boolean posIsBetweenBoundaries(int x, int y) {
        return (x == 1 && y == 0) ||
                (x == WIDTH - 2 && y == HEIGHT - 1) ||
                (XNum.intBetween(x, 1, WIDTH - 2) &&
                        XNum.intBetween(y, 1, HEIGHT - 2));
    }

    private static boolean posIsOutsideOfBlizzards(int x, int y, int minute) {
        for (Blizzard blizz : blizzardList) {
            int currentBlizzX = XUtils.cycleInt(blizz.x + blizz.dir.deltaX * minute, 1, WIDTH - 2);
            int currentBlizzY = XUtils.cycleInt(blizz.y + blizz.dir.deltaY * minute, 1, HEIGHT - 2);
            if (currentBlizzX == x && currentBlizzY == y) {
                return false;
            }
        }
        return true;
    }

    private static void parseInputToBlizzardList(List<String> input) {
        Set<Character> directions = new HashSet<>(Arrays.asList('<', '>', '^', 'v'));
        for (int y = 0; y < HEIGHT; y++) {
            String line = input.get(y);
            for (int x = 0; x < WIDTH; x++) {
                char chr = line.charAt(x);
                if (directions.contains(chr)) {
                    Blizzard blizz = Blizzard.from(x, y);
                    switch (chr) {
                        case '<':
                            blizz.dir = PosDelta4.left();
                            break;
                        case '>':
                            blizz.dir = PosDelta4.right();
                            break;
                        case '^':
                            blizz.dir = PosDelta4.up();
                            break;
                        case 'v':
                            blizz.dir = PosDelta4.down();
                            break;
                    }
                    blizzardList.add(blizz);
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private static class Blizzard {
        int x;
        int y;
        PosDelta4 dir;

        public static Blizzard from(int x, int y) {
            Blizzard blizz = new Blizzard();
            blizz.x = x;
            blizz.y = y;
            return blizz;
        }
    }

    private static class Node {
        int x;
        int y;
        int minute;

        public Node(int x, int y, int minute) {
            this.x = x;
            this.y = y;
            this.minute = minute;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y && minute == node.minute;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, minute);
        }
    }

    private static class Move {
        int deltaX;
        int deltaY;

        public Move(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
    }

}
