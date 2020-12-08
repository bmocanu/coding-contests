package ws.bmocanu.aoc.support;

import java.io.*;

import ws.bmocanu.aoc.utils.FileUtils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Log {

    private static Object[] part1Results;
    private static Object[] part2Results;

    public static void part1(Object... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format(format, params);
        appendLine("PART 1: " + line);
        part1Results = params;
    }

    public static void part2(Object... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format(format, params);
        appendLine("PART 2: " + line);
        part2Results = params;
    }

    public static void info(String message, Object... params) {
        String line = String.format(message, params);
        appendLine("INFO: " + line);
    }

    public static void error(String message, Object... params) {
        String line = String.format(message, params);
        appendLine("ERROR: " + line);
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

    public static Object[] getPart2Results() {
        return part2Results;
    }

    public static int getPart2Int() {
        return (Integer) part2Results[0];
    }

    public static void reset() {
        part1Results = null;
        part2Results = null;
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
