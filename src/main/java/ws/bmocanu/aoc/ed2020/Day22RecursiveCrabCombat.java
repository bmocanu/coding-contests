package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day22RecursiveCrabCombat extends SolutionBase {

    //30138
    //31587

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day22"));
        List<Integer> original1 = new ArrayList<>();
        List<Integer> original2 = new ArrayList<>();
        List<Integer> cursor = original1;
        for (String line : stringLines) {
            if (line.startsWith("Player 1")) {
                cursor = original1;
            } else if (line.startsWith("Player 2")) {
                cursor = original2;
            } else if (!line.isBlank()) {
                cursor.add(Integer.parseInt(line));
            }
        }

        calculatePart1(new ArrayList<>(original1), new ArrayList<>(original2));
        calculatePart2(original1, original2, 0);
    }

    public static void calculatePart1(List<Integer> deck1, List<Integer> deck2) {
        while (deck1.size() > 0 && deck2.size() > 0) {
            int card1 = deck1.get(0);
            int card2 = deck2.get(0);
            deck1.remove(0);
            deck2.remove(0);
            if (card1 > card2) {
                deck1.add(card1);
                deck1.add(card2);
            } else {
                deck2.add(card2);
                deck2.add(card1);
            }
        }
        Log.part1(winnerDeckScore(deck1, deck2));
    }

    public static int calculatePart2(List<Integer> deck1, List<Integer> deck2, int depth) {
        Set<String> gameHistory = new HashSet<>();
        while (true) {
            if (deck1.size() == 0 || deck2.size() == 0) {
                if (depth == 0) {
                    Log.part2(winnerDeckScore(deck1, deck2));
                    return 0;
                } else {
                    return (deck1.size() == 0 ? 2 : 1);
                }
            }
            String decksHash = decksHash(deck1, deck2);
            if (gameHistory.contains(decksHash)) {
                return 1;
            }
            gameHistory.add(decksHash);

            int card1 = deck1.get(0);
            int card2 = deck2.get(0);
            deck1.remove(0);
            deck2.remove(0);

            if (deck1.size() >= card1 && deck2.size() >= card2) {
                int winner = calculatePart2(copy(deck1, card1), copy(deck2, card2), depth + 1);
                if (winner == 1) {
                    deck1.add(card1);
                    deck1.add(card2);
                } else {
                    deck2.add(card2);
                    deck2.add(card1);
                }
            } else {
                if (card1 > card2) {
                    deck1.add(card1);
                    deck1.add(card2);
                } else {
                    deck2.add(card2);
                    deck2.add(card1);
                }
            }
        }
    }

    private static List<Integer> copy(List<Integer> deck, int nrToCopy) {
        List<Integer> result = new ArrayList<>();
        for (int index = 0; index < nrToCopy; index++) {
            result.add(deck.get(index));
        }
        return result;
    }

    private static String decksHash(List<Integer> deck1, List<Integer> deck2) {
        StringBuilder builder = new StringBuilder(200);
        builder.append("P1:");
        for (Integer d1 : deck1) {
            builder.append(d1).append(',');
        }
        builder.append("P2:");
        for (Integer d2 : deck2) {
            builder.append(d2).append(',');
        }
        return builder.toString();
    }

    private static int winnerDeckScore(List<Integer> deck1, List<Integer> deck2) {
        List<Integer> cursor = (deck1.size() > 0 ? deck1 : deck2);
        int sum = 0;
        for (int index = 0; index < cursor.size(); index++) {
            sum += (cursor.size() - index) * cursor.get(index);
        }
        return sum;
    }

}
