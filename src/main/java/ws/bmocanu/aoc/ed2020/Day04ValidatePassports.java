package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SReg;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;

public class Day04ValidatePassports {

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList("day04");
        List<Map<String, String>> passList = new ArrayList<>();

        Map<String, String> currentPass = new HashMap<>();
        int correctPass = 0;
        for (String line : stringLines) {
            if (line.trim().isEmpty()) {
                if ((currentPass.size() == 7 && !currentPass.containsKey("cid")) || currentPass.size() == 8) {
                    correctPass++;
                    passList.add(currentPass);
                }
                currentPass = new HashMap<>();
            } else {
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    String id = token.substring(0, token.indexOf(':'));
                    if (Utils.stringOneOf(id, "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")) {
                        currentPass.put(id, token.substring(token.indexOf(':') + 1));
                    }
                }
            }
        }

        Log.part1(correctPass);

        int correctPass2 = 0;
        for (Map<String, String> pass : passList) {
            boolean allValid = true;
            for (Map.Entry<String, String> entry : pass.entrySet()) {
                String id = entry.getKey();
                String value = entry.getValue();
                if (id.equals("byr")) {
                    allValid = allValid && SReg.parse("\\d+", value).intBtw(0, 1920, 2002);
                }
                if (id.equals("iyr")) {
                    allValid = allValid && SReg.parse("\\d+", value).intBtw(0, 2010, 2020);
                }
                if (id.equals("eyr")) {
                    allValid = allValid && SReg.parse("\\d+", value).intBtw(0, 2020, 2030);
                }
                if (id.equals("hgt")) {
                    if (!value.endsWith("cm") && !value.endsWith("in")) {
                        allValid = false;
                    } else {
                        String unit = value.substring(value.length() - 2);
                        if (unit.equals("cm")) {
                            allValid = allValid && SReg.parse("(\\d+)cm", value).intBtw(1, 150, 193);
                        } else {
                            allValid = allValid && SReg.parse("(\\d+)in", value).intBtw(1, 59, 76);
                        }
                    }
                }
                if (id.equals("hcl")) {
                    allValid = allValid && SReg.matches("#[0-9a-f]{6}", value);
                }
                if (id.equals("ecl")) {
                    allValid = allValid && Utils.stringOneOf(value, "amb", "blu", "brn", "gry", "grn", "hzl", "oth");
                }
                if (id.equals("pid")) {
                    allValid = allValid && SReg.matches("\\d{9}", value);
                }
                if (!allValid) {
                    break;
                }
            }
            if (allValid) {
                correctPass2++;
            }
        }

        Log.part2(correctPass2);
    }

}
