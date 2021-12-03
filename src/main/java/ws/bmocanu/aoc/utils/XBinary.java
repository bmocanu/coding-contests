package ws.bmocanu.aoc.utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class XBinary {

    public static int toInt(char[] number) {
        return Integer.parseInt(new String(number), 2);
    }

    public static long toLong(char[] number) {
        return Long.parseLong(new String(number), 2);
    }

}
