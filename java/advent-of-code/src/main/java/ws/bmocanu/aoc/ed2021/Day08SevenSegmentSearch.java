package ws.bmocanu.aoc.ed2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import static ws.bmocanu.aoc.utils.XUtils.intOneOf;
import static ws.bmocanu.aoc.utils.XUtils.lettersInCommon;
import static ws.bmocanu.aoc.utils.XUtils.sortChars;

public class Day08SevenSegmentSearch extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day08"));
        List<String> uniqueSegments = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split("\\|");
            String output = parts[1].trim();
            String[] outputParts = output.split(" ");
            for (String part : outputParts) {
                if (intOneOf(part.length(), 2, 3, 4, 7)) {
                    uniqueSegments.add(part);
                }
            }
        }
        Log.part1(uniqueSegments.size());

        Map<String, Integer> strToDigit = new HashMap<>();
        int part2Result = 0;

        for (String line : input) {
            String[] parts = line.split("\\|");
            String[] inputParts = parts[0].trim().split(" ");
            String[] outputParts = parts[1].trim().split(" ");
            String p1 = null;
            String p4 = null;
            for (String in : inputParts) {
                switch (in.length()) {
                    case 2: {
                        strToDigit.put(sortChars(in), 1);
                        p1 = in;
                        break;
                    }
                    case 3: {
                        strToDigit.put(sortChars(in), 7);
                        break;
                    }
                    case 4: {
                        strToDigit.put(sortChars(in), 4);
                        p4 = in;
                        break;
                    }
                    case 7:
                        strToDigit.put(sortChars(in), 8);
                        break;
                }
            }

            for (String in : inputParts) {
                switch (in.length()) {
                    case 5: {
                        // digits 2 3 5
                        if (lettersInCommon(in, p1) == 2) {
                            strToDigit.put(sortChars(in), 3);
                        } else {
                            // digits 2 5
                            if (lettersInCommon(in, p4) == 3) {
                                strToDigit.put(sortChars(in), 5);
                            } else {
                                strToDigit.put(sortChars(in), 2);
                            }
                        }
                        break;
                    }
                    case 6: {
                        // digits 0 6 9
                        if (lettersInCommon(in, p1) == 2) {
                            // digits 0 9
                            if (lettersInCommon(in, p4) == 4) {
                                strToDigit.put(sortChars(in), 9);
                            } else {
                                strToDigit.put(sortChars(in), 0);
                            }
                        } else {
                            strToDigit.put(sortChars(in), 6);
                        }
                        break;
                    }
                }
            }

            int nr = 0;
            int nrTen = 1;
            for (int index = outputParts.length - 1; index >= 0; index--) {
                String sortedOut = sortChars(outputParts[index]);
                nr += strToDigit.get(sortedOut) * nrTen;
                nrTen *= 10;
            }

            part2Result += nr;
        }

        Log.part2(part2Result);
    }

}
