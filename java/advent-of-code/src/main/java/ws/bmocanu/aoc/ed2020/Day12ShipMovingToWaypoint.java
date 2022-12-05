package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day12ShipMovingToWaypoint extends SolutionBase {

    public static class Command {
        String command;
        int value;
    }

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day12"));
        SBind<Command> binder = new SBind("(\\w)(\\d+)", Command.class, "command", "value");
        List<Command> commands = new ArrayList<>();
        for (String line : stringLines) {
            commands.add(binder.bindOne(line));
        }

        Point p = new Point();
        int dir4 = 1;

        for (Command command : commands) {
            switch (command.command) {
                case "F": {
                    PosDelta4 posDelta = PosDelta4.fromDir4(dir4);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "L": {
                    for (int index = 0; index < command.value / 90; index++) {
                        dir4 = XUtils.cycleInt(dir4 - 1, 0, 3);
                    }
                    break;
                }
                case "R": {
                    for (int index = 0; index < command.value / 90; index++) {
                        dir4 = XUtils.cycleInt(dir4 + 1, 0, 3);
                    }
                    break;
                }
                case "N": {
                    PosDelta4 posDelta = PosDelta4.fromNorth();
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "S": {
                    PosDelta4 posDelta = PosDelta4.fromSouth();
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "E": {
                    PosDelta4 posDelta = PosDelta4.fromEast();
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "W": {
                    PosDelta4 posDelta = PosDelta4.fromWest();
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
            }
        }

        Log.part1(XNum.abs(p.x) + XNum.abs(p.y));

        Point wp = new Point();
        wp.x = 10;
        wp.y = -1;
        p = new Point();

        for (Command command : commands) {
            switch (command.command) {
                case "F": {
                    p.x += wp.x * command.value;
                    p.y += wp.y * command.value;
                    break;
                }
                case "L": {
                    PosDelta8 delta = PosDelta8.fromPointInQuadrant(wp);
                    for (int index = 0; index < command.value / 90; index++) {
                        delta.rotateLeft().rotateLeft();
                        int x = wp.x;
                        int y = wp.y;
                        wp.x = XNum.abs(y) * (delta.deltaX);
                        wp.y = XNum.abs(x) * (delta.deltaY);
                    }
                    break;
                }
                case "R": {
                    PosDelta8 delta = PosDelta8.fromPointInQuadrant(wp);
                    for (int index = 0; index < command.value / 90; index++) {
                        delta.rotateRight().rotateRight();
                        int x = wp.x;
                        int y = wp.y;
                        wp.x = XNum.abs(y) * (delta.deltaX);
                        wp.y = XNum.abs(x) * (delta.deltaY);
                    }
                    break;
                }
                case "N": {
                    PosDelta4 posDelta = PosDelta4.fromNorth();
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "S": {
                    PosDelta4 posDelta = PosDelta4.fromSouth();
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "E": {
                    PosDelta4 posDelta = PosDelta4.fromEast();
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "W": {
                    PosDelta4 posDelta = PosDelta4.fromWest();
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
            }
        }

        Log.part2(XNum.abs(p.x) + XNum.abs(p.y));
    }

}
