package ws.bmocanu.aoc.utils;

import java.io.*;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Log {

    public static void part1(String... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format("Part 1: " + format + '\n', (Object[]) params);
        appendLine(line);
    }

    public static void part1(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        String line = String.format("Part 1: " + format + '\n', (Object[]) params);
        appendLine(line);
    }

    public static void part2(String... params) {
        String format = "[%s] ".repeat(params.length);
        String line = String.format("Part 2: " + format + '\n', (Object[]) params);
        appendLine(line);
    }

    public static void part2(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        String line = String.format("Part 2: " + format + '\n', (Object[]) params);
        appendLine(line);
    }

    public static void info(String message, Object... params) {
        String line = String.format(message, params);
        appendLine(line);
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
        System.out.print(line);
        if (writer != null) {
            try {
                writer.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
