package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day22MonkeyMap extends SolutionBase {

    private static final int NOTHING = 0;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day22"));
        List<String> boardInput = extractBoardInput(input);
        List<Command> commands = extractCommandInput(input);

        // Part 1
        Log.part1(tracePathAndReturnScore(boardInput, commands, Day22MonkeyMap::getPointAroundTheTableP1));

        // Part 2
        Log.part2(tracePathAndReturnScore(boardInput, commands, Day22MonkeyMap::getPointAroundTheTableP2));
    }

    // ----------------------------------------------------------------------------------------------------

    private static int tracePathAndReturnScore(List<String> boardInput, List<Command> commands,
                                               PathAroundProvider pathAroundProvider) {
        FlexStruct flex = FlexStruct.fromLineList(boardInput);
        int SPACE = 1;
        int WALL = 2;
        flex.forAllPoints().mapData()
                .charToType('.', SPACE)
                .charToType('#', WALL);

        Point start = null;
        for (int x = 0; x < flex.width(); x++) {
            start = flex.pointOrNull(x, 0);
            if (start != null && start.type == SPACE) {
                break;
            }
        }

        Point cursor = start;
        PosDelta4 dir = PosDelta4.right();
        for (Command command : commands) {
            if (command.command != null) {
                dir = PosDelta4.fromDir4(XUtils.cycleInt(dir.dir4 + (command.command == 'L' ? -1 : 1), 0, 3));
            } else {
                for (int step = 0; step < command.steps; step++) {
                    Point nextPoint = flex.pointOrNull(cursor, dir);
                    if (nextPoint == null || nextPoint.type == NOTHING) {
                        Tuple tuple = pathAroundProvider.getPointAroundTheTable(cursor, dir, flex);
                        nextPoint = tuple.point;
                        if (nextPoint != null && nextPoint.type == SPACE) {
                            dir = tuple.dir;
                        }
                    }
                    if (nextPoint != null && nextPoint.type == SPACE) {
                        cursor = nextPoint;
                    }
                }
            }
        }
        return calculateScore(cursor, dir);
    }

    // ----------------------------------------------------------------------------------------------------

    private static class Command {
        int steps;
        Character command;

        public Command(int steps) {
            this.steps = steps;
        }

        public Command(Character command) {
            this.command = command;
        }
    }

    private interface PathAroundProvider {
        Tuple getPointAroundTheTable(Point cursor, PosDelta4 dir, FlexStruct flex);
    }

    private static class Tuple {
        Point point;
        PosDelta4 dir;

        public Tuple(Point point, PosDelta4 dir) {
            this.point = point;
            this.dir = dir;
        }
    }

    @SuppressWarnings({"ReassignedVariable", "DataFlowIssue"})
    private static int calculateScore(Point point, PosDelta4 dir) {
        int score = 1000 * (point.y + 1) + 4 * (point.x + 1);
        switch (dir.dir4) {
            case 0:
                score += 3;
                break;
            case 1:
                score += 0;
                break;
            case 2:
                score += 1;
                break;
            case 3:
                score += 2;
                break;
        }
        return score;
    }

    private static Tuple getPointAroundTheTableP1(Point cursor, PosDelta4 dir, FlexStruct flex) {
        int startX = cursor.x;
        int startY = cursor.y;
        if (dir.dir4 == PosDelta4.up().dir4) {
            startY = flex.height();
        } else if (dir.dir4 == PosDelta4.down().dir4) {
            startY = 0;
        } else if (dir.dir4 == PosDelta4.left().dir4) {
            startX = flex.width();
        } else if (dir.dir4 == PosDelta4.right().dir4) {
            startX = 0;
        }

        Point candidate = flex.point(startX, startY);
        while (candidate.type == NOTHING) {
            candidate = flex.point(candidate, dir);
        }
        return new Tuple(candidate, dir);
    }

    private static Tuple getPointAroundTheTableP2(Point cursor, PosDelta4 dir, FlexStruct flex) {
        int startX = cursor.x;
        int startY = cursor.y;
        PosDelta4 startDir = dir;
        // Face 0
        // -- going up is fine
        // -- going down is fine
        // -- going left
        if (cursor.x == 50 && XNum.intBetween(cursor.y, 50, 99) && dir.isLeft()) {
            startX = cursor.y - 50;
            startY = 100;
            startDir = PosDelta4.down();
        }
        // -- going right
        if (cursor.x == 99 && XNum.intBetween(cursor.y, 50, 99) && dir.isRight()) {
            startX = 100 + cursor.y - 50;
            startY = 49;
            startDir = PosDelta4.up();
        }

        // Face 1
        // -- going up
        if (XNum.intBetween(cursor.x, 50, 99) && cursor.y == 0 && dir.isUp()) {
            startX = 0;
            startY = 100 + cursor.x;
            startDir = PosDelta4.right();
        }
        // -- going down is fine
        // -- going left
        if (cursor.x == 50 && XNum.intBetween(cursor.y, 0, 49) && dir.isLeft()) {
            startX = 0;
            startY = 149 - cursor.y;
            startDir = PosDelta4.right();
        }
        // -- going right is fine

        // Face 2
        // -- going up
        if (XNum.intBetween(cursor.x, 100, 149) && cursor.y == 0 && dir.isUp()) {
            startX = cursor.x - 100;
            startY = 199;
            startDir = PosDelta4.up();
        }
        // -- going down
        if (XNum.intBetween(cursor.x, 100, 149) && cursor.y == 49 && dir.isDown()) {
            startX = 99;
            startY = cursor.x - 50;
            startDir = PosDelta4.left();
        }
        // -- going left is fine
        // -- going right
        if (cursor.x == 149 && XNum.intBetween(cursor.y, 0, 49) && dir.isRight()) {
            startX = 99;
            startY = 149 - cursor.y;
            startDir = PosDelta4.left();
        }

        // Face 3
        // -- going up is fine
        // -- going down
        if (XNum.intBetween(cursor.x, 50, 99) && cursor.y == 149 && dir.isDown()) {
            startX = 49;
            startY = 100 + cursor.x;
            startDir = PosDelta4.left();
        }
        // -- going left is fine
        // -- going right
        if (cursor.x == 99 && XNum.intBetween(cursor.y, 100, 149) && dir.isRight()) {
            startX = 149;
            startY = 149 - cursor.y;
            startDir = PosDelta4.left();
        }

        // Face 4
        // -- going up
        if (XNum.intBetween(cursor.x, 0, 49) && cursor.y == 100 && dir.isUp()) {
            startX = 50;
            startY = 50 + cursor.x;
            startDir = PosDelta4.right();
        }
        // -- going down is fine
        // -- going left
        if (cursor.x == 0 && XNum.intBetween(cursor.y, 100, 149) && dir.isLeft()) {
            startX = 50;
            startY = 149 - cursor.y;
            startDir = PosDelta4.right();
        }
        // -- going right is fine

        // Face 5
        // -- going up is fine
        // -- going down
        if (XNum.intBetween(cursor.x, 0, 49) && cursor.y == 199 && dir.isDown()) {
            startX = 100 + cursor.x;
            startY = 0;
            startDir = PosDelta4.down();
        }
        // -- going left
        if (cursor.x == 0 && XNum.intBetween(cursor.y, 150, 199) && dir.isLeft()) {
            startX = cursor.y - 100;
            startY = 0;
            startDir = PosDelta4.down();
        }
        // -- going right
        if (cursor.x == 49 && XNum.intBetween(cursor.y, 150, 199) && dir.isRight()) {
            startX = cursor.y - 100;
            startY = 149;
            startDir = PosDelta4.up();
        }

        Point candidate = flex.point(startX, startY);
        while (candidate.type == NOTHING) {
            candidate = flex.point(candidate, startDir);
        }
        return new Tuple(candidate, startDir);
    }

    private static List<String> extractBoardInput(List<String> input) {
        List<String> result = new LinkedList<>();
        for (String line : input) {
            if (line.trim().isEmpty()) {
                break;
            } else {
                result.add(line);
            }
        }
        return result;
    }

    private static List<Command> extractCommandInput(List<String> input) {
        List<String> commandLines = new LinkedList<>();
        boolean startAdding = false;
        for (String line : input) {
            if (line.trim().isEmpty()) {
                startAdding = true;
            } else if (startAdding) {
                commandLines.add(line);
            }
        }

        List<Command> commands = new ArrayList<>();
        String number = "";
        for (String commandLine : commandLines) {
            for (Character chr : commandLine.toCharArray()) {
                if (Character.isDigit(chr)) {
                    number = number + chr;
                } else {
                    if (number.trim().length() > 0) {
                        commands.add(new Command(Integer.parseInt(number)));
                        number = "";
                        if (chr == 'L' || chr == 'R') {
                            commands.add(new Command(chr));
                        }
                    }
                }
            }
        }
        if (number.trim().length() > 0) {
            commands.add(new Command(Integer.parseInt(number)));
        }
        return commands;
    }

}

