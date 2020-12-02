package ws.bmocanu.aoc.support;

import ws.bmocanu.aoc.utils.FileUtils;

import java.io.*;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Log {

    public static void part1(String... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format(format + '\n', (Object[]) params);
        appendLine("PART 1: " + line);
    }

    public static void part1(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        String line = String.format(format, (Object[]) params);
        appendLine("PART 1: " + line);
    }

    public static void part2(String... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format(format, (Object[]) params);
        appendLine("PART 2: " + line);
    }

    public static void part2(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        String line = String.format(format, (Object[]) params);
        appendLine("PART 2: " + line);
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
