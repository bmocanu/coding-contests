package ws.bmocanu.aoc.ed2020;

import java.util.*;

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
                    if (Utils.strOneOf(id, "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")) {
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
                    allValid &= SReg.intBetween("\\d+", 1920, 2002, value);
                }
                if (id.equals("iyr")) {
                    allValid &= SReg.intBetween("\\d+", 2010, 2020, value);
                }
                if (id.equals("eyr")) {
                    allValid &= SReg.intBetween("\\d+", 2020, 2030, value);
                }
                if (id.equals("hgt")) {
                    if (!value.endsWith("cm") && !value.endsWith("in")) {
                        allValid = false;
                    } else {
                        String unit = value.substring(value.length() - 2);
                        if (unit.equals("cm")) {
                            allValid &= SReg.intBetween("(\\d+)cm", 150, 193, value);
                        } else {
                            allValid &= SReg.intBetween("(\\d+)in", 59, 76, value);
                        }
                    }
                }
                if (id.equals("hcl")) {
                    if (!value.startsWith("#") || value.length() != 7) {
                        allValid = false;
                    } else {
                        String color = value.substring(1);
                        for (char chr : color.toCharArray()) {
                            if (chr < '0' || (chr > '9' && chr < 'a') || (chr > 'f')) {
                                allValid = false;
                                break;
                            }
                        }
                    }
                }
                if (id.equals("ecl")) {
                    Set<String> color = new HashSet<>(Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth"));
                    if (!color.contains(value)) {
                        allValid = false;
                    }
                }
                if (id.equals("pid")) {
                    if (value.length() != 9) {
                        allValid = false;
                    } else {
                        for (char chr : value.toCharArray()) {
                            if (!Utils.charIsDigit(chr)) {
                                allValid = false;
                                break;
                            }
                        }
                    }
                }
                if (!allValid) {
                    break;
                }
            }
            if (allValid) {
                correctPass2++;
            }
        }
//        byr (Birth Year) - four digits; at least 1920 and at most 2002.
//        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//        hgt (Height) - a number followed by either cm or in:
//        If cm, the number must be at least 150 and at most 193.
//        If in, the number must be at least 59 and at most 76.
//        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//        pid (Passport ID) - a nine-digit number, including leading zeroes.
//        cid (Country ID) - ignored, missing or not.

        Log.part2(correctPass2);
    }

}
