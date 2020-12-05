package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;

public class Day05SeatIdHalfing {

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList("day05");

        List<Long> seats = new ArrayList<>();
        long maxSeatId = 0;
        for (String line : stringLines) {
            long seatId = getSeatId(line, 0, 0, 127);
            seats.add(seatId);
            if (seatId > maxSeatId) {
                maxSeatId = seatId;
            }
        }
        Log.part1(maxSeatId);

        Collections.sort(seats);
        for (int index = 1; index < seats.size(); index++) {
            if (seats.get(index) - seats.get(index - 1) == 2) {
                Log.part2(seats.get(index - 1) + 1);
                break;
            }
        }
    }

    public static long getSeatId(String input, int index, int left, int right) {
        if (right - left > 0) {
            char chr = input.charAt(index);
            if (chr == 'F') {
                return getSeatId(input, index + 1, left, right - (right - left) / 2 - 1);
            } else if (chr == 'B') {
                return getSeatId(input, index + 1, left + (right - left) / 2 + 1, right);
            }
        } else {
            int bitX = 0;
            int bitY = 7;
            for (int localIndex = index; localIndex < input.length(); localIndex++) {
                if (input.charAt(localIndex) == 'L') {
                    bitY = bitY - (bitY - bitX) / 2 - 1;
                } else {
                    bitX = bitX + (bitY - bitX) / 2 + 1;
                }
            }
            return left * 8 + bitX;
        }
        return 0;
    }

}