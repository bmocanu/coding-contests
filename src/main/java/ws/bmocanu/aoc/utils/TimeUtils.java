package ws.bmocanu.aoc.utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class TimeUtils {

    public static String formatDurationInHoursMinutesSeconds(long durationInMs) {
        if (durationInMs == 0) {
            return "1 second";
        }
        long durationInSec = durationInMs / 1000;
        int hours = (int) (durationInSec / 3600);
        int minutes = (int) ((durationInSec % 3600) / 60);
        int seconds = (int) (durationInSec - (hours * 3600) - (minutes * 60));
        StringBuilder builder = new StringBuilder();
        if (hours > 0) {
            builder.append(hours).append(hours > 1 ? " hours " : " hour ");
        }
        if (minutes > 0) {
            builder.append(minutes).append(minutes > 1 ? " minutes " : " minute ");
        }
        if (seconds > 0) {
            builder.append(seconds).append(seconds > 1 ? " seconds " : " second ");
        }
        return builder.toString();
    }

    public static String formatDurationInSecAndMs(long durationInMs) {
        if (durationInMs == 0) {
            return "1 ms";
        }
        int seconds = (int) durationInMs / 1000;
        int remMs = (int) durationInMs % 1000;
        StringBuilder builder = new StringBuilder();
        if (seconds > 0) {
            builder.append(seconds).append(" sec ");
        }
        if (remMs > 0) {
            builder.append(remMs).append(" ms");
        }
        return builder.toString();
    }

}
