package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day03RucksackReorganization extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day03"));
        int total1 = 0;
        for (String line : input) {
            String h1 = line.substring(0, line.length() / 2);
            String h2 = line.substring(line.length() / 2);
            for (char ch : h1.toCharArray()) {
                if (h2.indexOf(ch) >= 0) {
                    total1 += scoreForChar(ch);
                    break;
                }
            }
        }

        boolean[] processed = new boolean[input.size()];
        int total2 = 0;
        for (int x = 0; x < input.size() - 2; x++) {
            if (!processed[x]) {
                middle_loop:
                for (int y = x + 1; y < input.size() - 1; y++) {
                    if (!processed[y]) {
                        for (int z = y + 1; z < input.size(); z++) {
                            if (!processed[z]) {
                                for (char ch : input.get(x).toCharArray()) {
                                    if (input.get(y).indexOf(ch) >= 0 &&
                                            input.get(z).indexOf(ch) >= 0) {
                                        processed[y] = true;
                                        processed[z] = true;
                                        total2 += scoreForChar(ch);
                                        break middle_loop;
                                    }
                                }
                            }
                        }
                    }
                }
                processed[x] = true;
            }
        }

        Log.part1(total1);
        Log.part2(total2);
    }

    private static int scoreForChar(char ch) {
        if (Character.isLowerCase(ch)) {
            return (ch - 'a') + 1;
        } else {
            return (ch - 'A') + 27;
        }
    }

}
