package ws.bmocanu.aoc.support;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.TimeUtils;
import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Log {

    private static long startTimestamp = System.currentTimeMillis();
    private static long part1Timestamp;
    private static long part1TimeInMs;
    private static long part2TimeInMs;

    private static Object[] part1Results;
    private static Object[] part2Results;

    public static void part1(Object... params) {
        part1Timestamp = System.currentTimeMillis();
        part1TimeInMs = part1Timestamp - startTimestamp;
        String format = "[%s] ".repeat(params.length);
        format = format.substring(0, format.length() - 1);
        String line = String.format(format, params);
        appendLine("PART 1: " + line + ", exec time: [" + TimeUtils.formatDurationInSecAndMs(part1TimeInMs) + "]");
        part1Results = params;
    }

    public static void part2(Object... params) {
        part2TimeInMs = System.currentTimeMillis() - part1Timestamp;
        String format = "[%s] ".repeat(params.length);
        format = format.substring(0, format.length() - 1);
        String line = String.format(format, params);
        appendLine("PART 2: " + line + ", exec time: [" + TimeUtils.formatDurationInSecAndMs(part2TimeInMs) + "]");
        part2Results = params;
    }

    public static void info(String message, Object... params) {
        String line = String.format(message, params);
        appendLine("INFO: " + line);
    }

    public static void info(Object... params) {
        String line = Arrays.toString(params);
        appendLine("INFO: " + line);
    }

    public static void error(String message, Object... params) {
        String line = String.format(message, params);
        appendLine("ERROR: " + line);
    }

    public static void error(Object... params) {
        String line = Arrays.toString(params);
        appendLine("ERROR: " + line);
    }

    public static void array(int[] array) {
        info(Utils.intArrayToString(array));
    }

    public static void array(long[] array) {
        info(Utils.longArrayToString(array));
    }

    public static void array(String[] array) {
        info(Utils.stringArrayToString(array));
    }

    public static void list(List<?> list) {
        info(list);
    }

    public static void error(Throwable throwable, String message, Object... params) {
        StringWriter sw = new StringWriter(300);
        throwable.printStackTrace(new PrintWriter(sw));
        String line = String.format(message, params);
        appendLine("ERROR: " + line + "\nCaused by: " + sw.toString());
    }

    // ----------------------------------------------------------------------------------------------------

    public static Object[] getPart1Results() {
        return part1Results;
    }

    public static int getPart1Int() {
        return (Integer) part1Results[0];
    }

    public static long getPart1Long() {
        return (Long) part1Results[0];
    }

    public static Object[] getPart2Results() {
        return part2Results;
    }

    public static int getPart2Int() {
        return (Integer) part2Results[0];
    }

    public static long getPart2Long() {
        return (Long) part2Results[0];
    }

    public static void reset() {
        part1Results = null;
        part2Results = null;
        startTimestamp = System.currentTimeMillis();
        part1Timestamp = 0;
        part1TimeInMs = 0;
        part2TimeInMs = 0;
    }

    // ----------------------------------------------------------------------------------------------------

    private static Writer writer;

    public static void appendToTimestampedFile(String filePrefix) {
        File appendFile = FileUtils.getTimestampedOutputFile(filePrefix);
        try {
            writer = new OutputStreamWriter(new FileOutputStream(appendFile));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to open append file for Log", e);
        }
    }

    private static void appendLine(String line) {
        String localLine = line + '\n';
        System.out.print(localLine);
        if (writer != null) {
            try {
                writer.append(localLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
