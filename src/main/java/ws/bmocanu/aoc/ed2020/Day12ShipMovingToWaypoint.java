package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day12ShipMovingToWaypoint extends SolutionBase {

    public static class Command {
        String command;
        int value;
    }

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day12"));
        SBinder binder = new SBinder("(\\w)(\\d+)", "command", "value");
        List<Command> commands = new ArrayList<>();
        for (String line : stringLines) {
            commands.add(binder.bind(line, Command.class));
        }

        Point p = new Point();
        int dir4 = 1;

        for (Command command : commands) {
            switch (command.command) {
                case "F": {
                    PosDelta posDelta = PosDelta.byDir4(dir4);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "L": {
                    for (int index = 0; index < command.value / 90; index++) {
                        dir4 = Utils.cycleInt(dir4 - 1, 0, 3);
                    }
                    break;
                }
                case "R": {
                    for (int index = 0; index < command.value / 90; index++) {
                        dir4 = Utils.cycleInt(dir4 + 1, 0, 3);
                    }
                    break;
                }
                case "N": {
                    PosDelta posDelta = PosDelta.byDir4(0);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "S": {
                    PosDelta posDelta = PosDelta.byDir4(2);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "E": {
                    PosDelta posDelta = PosDelta.byDir4(1);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
                case "W": {
                    PosDelta posDelta = PosDelta.byDir4(3);
                    p.x += posDelta.deltaX * command.value;
                    p.y += posDelta.deltaY * command.value;
                    break;
                }
            }
        }

        Log.part1(Utils.abs(p.x) + Utils.abs(p.y));

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
                    if (command.value % 90 != 0) {
                        Log.error("Invalid angle");
                    }
                    PosDelta delta = PosDelta.byPoint(wp);
                    for (int index = 0; index < command.value / 90; index++) {
                        delta.rotateLeft().rotateLeft();
                        int x = wp.x;
                        int y = wp.y;
                        wp.x = Utils.abs(y) * (delta.deltaX);
                        wp.y = Utils.abs(x) * (delta.deltaY);
                    }
                    break;
                }
                case "R": {
                    if (command.value % 90 != 0) {
                        Log.error("Invalid angle");
                    }
                    PosDelta delta = PosDelta.byPoint(wp);
                    for (int index = 0; index < command.value / 90; index++) {
                        delta.rotateRight().rotateRight();
                        int x = wp.x;
                        int y = wp.y;
                        wp.x = Utils.abs(y) * (delta.deltaX);
                        wp.y = Utils.abs(x) * (delta.deltaY);
                    }
                    break;
                }
                case "N": {
                    PosDelta posDelta = PosDelta.byDir4(0);
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "S": {
                    PosDelta posDelta = PosDelta.byDir4(2);
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "E": {
                    PosDelta posDelta = PosDelta.byDir4(1);
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
                case "W": {
                    PosDelta posDelta = PosDelta.byDir4(3);
                    wp.x += posDelta.deltaX * command.value;
                    wp.y += posDelta.deltaY * command.value;
                    break;
                }
            }
        }

        Log.part2(Utils.abs(p.x) + Utils.abs(p.y));
    }

}
