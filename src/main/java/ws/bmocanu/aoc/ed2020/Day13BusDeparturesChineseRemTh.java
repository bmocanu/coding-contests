package ws.bmocanu.aoc.ed2020;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day13BusDeparturesChineseRemTh extends SolutionBase {

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day13"));
        int currentMinute = Integer.parseInt(stringLines.get(0));

        int[] busIds = new int[1000];
        int[] busInc = new int[1000];
        int index = 0;
        int inc = 0;
        StringTokenizer tokenizer = new StringTokenizer(stringLines.get(1), ",");
        while (tokenizer.hasMoreTokens()) {
            String busEntry = tokenizer.nextToken();
            if (!busEntry.equals("x")) {
                busIds[index] = Integer.parseInt(busEntry);
                busInc[index] = inc;
                index++;
            }
            inc++;
        }

        busIds = Arrays.copyOf(busIds, index);
        busInc = Arrays.copyOf(busInc, index);

        int min = Integer.MAX_VALUE;
        int minBus = 0;
        for (int bus : busIds) {
            int busTime = getNextArrival(bus, currentMinute);
            if (busTime < min) {
                min = busTime;
                minBus = bus;
            }
        }

        Log.part1((min - currentMinute) * minBus);

        long minValue = 0;
        long product = 1;
        for (index = 0; index < busIds.length; index++) {
            while ((minValue + busInc[index]) % busIds[index] != 0) {
                minValue += product;
            }
            product *= busIds[index];
        }

        Log.part2(minValue);
    }

    public static int getNextArrival(int bus, int currentMin) {
        int busTime = bus;
        while (busTime < currentMin) {
            busTime += bus;
        }
        return busTime;
    }

}
