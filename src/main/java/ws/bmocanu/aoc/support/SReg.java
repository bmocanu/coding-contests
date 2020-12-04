package ws.bmocanu.aoc.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SReg {

    public static boolean intBetween(String regexWithGroup, int minValue, int maxValue, String input) {
        Pattern pattern = Pattern.compile(regexWithGroup);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String value = (matcher.groupCount() > 1 ? matcher.group(1) : matcher.group());
            try {
                int nr = Integer.parseInt(value);
                if (nr >= minValue && nr <= maxValue) {
                    return true;
                }
            } catch (Exception ignored) {
                return false;
            }
        }
        return false;
    }

    public static boolean matchString(String regexWithGroup, String input) {
        Pattern pattern = Pattern.compile(regexWithGroup);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
