package ws.bmocanu.aoc.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SReg {

    private final Matcher matcher;
    private final boolean patternMatches;

    // ----------------------------------------------------------------------------------------------------

    public static SReg parse(String regexWithGroup, String input) {
        Pattern pattern = Pattern.compile(regexWithGroup);
        Matcher matcher = pattern.matcher(input);
        return new SReg(matcher, matcher.matches());
    }

    public static boolean matches(String regexWithGroup, String input) {
        Pattern pattern = Pattern.compile(regexWithGroup);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    // ----------------------------------------------------------------------------------------------------

    public SReg(Matcher matcher, boolean patternMatches) {
        this.matcher = matcher;
        this.patternMatches = patternMatches;
    }

    // ----------------------------------------------------------------------------------------------------

    public boolean intBtw(int index, int minValue, int maxValue) {
        if (!patternMatches) {
            return false;
        }
        String value = matcher.group(index);
        try {
            int nr = Integer.parseInt(value);
            if (nr >= minValue && nr <= maxValue) {
                return true;
            }
        } catch (Exception ignored) {
            // ignored exception
        }
        return false;
    }

    public boolean stringOneOf(int index, String... valuesToCheck) {
        if (!patternMatches) {
            return false;
        }
        String value = matcher.group(index);
        return Utils.stringOneOf(value, valuesToCheck);
    }

    public int getInt(int index) {
        if (!patternMatches) {
            return 0;
        }
        String value = matcher.group(index);
        try {
            return Integer.parseInt(value);
        } catch (Exception ignored) {
            // ignored exception
        }
        return 0;
    }

    public String getString(int index) {
        if (!patternMatches) {
            return null;
        }
        return matcher.group(index);
    }

}
