package ws.bmocanu.aoc.utils;

public class Log {

    public static void part1(String... params) {
        String format = "[%s] ".repeat(params.length);
        System.out.printf("Part 1: " + format + '\n', (Object[]) params);
    }

    public static void part1(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        System.out.printf("Part 1: " + format + '\n', (Object[]) params);
    }

    public static void part2(String... params) {
        String format = "[%s] ".repeat(params.length);
        System.out.printf("Part 2: " + format + '\n', (Object[]) params);
    }

    public static void part2(Integer... params) {
        String format = "[%d] ".repeat(params.length);
        System.out.printf("Part 2: " + format + '\n', (Object[]) params);
    }

    public static void info(String message, Object... params) {
        System.out.printf(message, (Object[]) params);
    }

}
