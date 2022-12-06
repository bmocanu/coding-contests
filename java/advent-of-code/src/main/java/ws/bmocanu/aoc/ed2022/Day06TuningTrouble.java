package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day06TuningTrouble extends SolutionBase {

    public static void main(String[] args) {
        String input = XRead.fileToOneString(filePath("day06"));
        for (int part = 1; part <= 2; part++) {
            int partLength = (part == 1 ? 4 : 14);
            for (int index = 0; index < input.length() - partLength; index++) {
                String marker = "";
                for (int x = index; x < index + partLength; x++) {
                    if (marker.indexOf(input.charAt(x)) < 0) {
                        marker = marker + input.charAt(x);
                    } else {
                        break;
                    }
                }
                if (marker.length() == partLength) {
                    Log.part(part, index + partLength);
                    break;
                }
            }
        }
    }

}
