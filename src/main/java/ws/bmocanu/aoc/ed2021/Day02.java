package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day02 extends SolutionBase {

    public static void main(String[] args) {
        List<String> commands = FileUtils.fileAsStringPerLineToStringList(filePath("day02"));
        int position = 0;
        int depth = 0;
        for(String command : commands) {
            if (command.startsWith("forward")) {
                position += Integer.parseInt(command.substring(8));
            }
            if (command.startsWith("up")) {
                depth -= Integer.parseInt(command.substring(3));
            }
            if (command.startsWith("down")) {
                depth += Integer.parseInt(command.substring(5));
            }
        }
        Log.part1(position * depth);
        position = 0;
        depth = 0;
        int aim = 0;
        for(String command : commands) {
            if (command.startsWith("forward")) {
                position += Integer.parseInt(command.substring(8));
                depth += aim * Integer.parseInt(command.substring(8));
            }
            if (command.startsWith("up")) {
                aim -= Integer.parseInt(command.substring(3));
            }
            if (command.startsWith("down")) {
                aim += Integer.parseInt(command.substring(5));
            }
        }
        Log.part2(position * depth);
    }

}
