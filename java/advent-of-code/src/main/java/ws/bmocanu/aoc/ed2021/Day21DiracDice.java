package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SMap;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day21DiracDice extends SolutionBase {

    public static void main(String[] args) {
        // {P1 start pos, P1 score}, {P2 start pos, P2 score}
        int POS = 0;
        int SCORE = 1;
        int[][] playerStats = new int[][]{{0, 0}, {0, 0}};
        playerStats[0][POS] = 10;
        playerStats[1][POS] = 4;

        int currentPlayer = 0;
        int die100 = 0;
        int die100Rolls = 0;

        while (true) {
            int steps = ++die100 + ++die100 + ++die100;
            die100Rolls += 3;
            playerStats[currentPlayer][POS] += steps;
            while (playerStats[currentPlayer][POS] > 10) {
                playerStats[currentPlayer][POS] -= 10;
            }
            playerStats[currentPlayer][SCORE] += playerStats[currentPlayer][POS];
            if (playerStats[currentPlayer][SCORE] >= 1000) {
                Log.part1(playerStats[1 - currentPlayer][SCORE] * die100Rolls);
                break;
            }
            currentPlayer = 1 - currentPlayer;
        }

        long[] wins = calcWins(1, 10, 0, 4, 0);
        Log.part2(XUtils.maxFromArray(wins));
    }

    // key   = "<currentPlayer>,<p1Pos>,<p1Score>,<p2Pos>,<p2Score>"
    // value = the number of combinations for that universe
    private static final SMap<String, long[]> preCalcWins = new SMap<>();
    private static final int[] diceCombinations = {
            111, 112, 121, 211, 122, 212, 221, 113, 131, 311, 123, 132, 231, 213, 312, 321, 222,
            223, 232, 322, 331, 313, 133, 233, 323, 332, 333
    };

    public static long[] calcWins(int currentPlayer, int p1Pos, int p1Score, int p2Pos, int p2Score) {
        if (p1Score >= 21) {
            return new long[]{1, 0};
        } else if (p2Score >= 21) {
            return new long[]{0, 1};
        }
        long p1Wins = 0;
        long p2Wins = 0;
        for (int diceCombination : diceCombinations) {
            int newP1Pos = p1Pos;
            int newP1Score = p1Score;
            int newP2Pos = p2Pos;
            int newP2Score = p2Score;
            if (currentPlayer == 1) {
                newP1Pos = p1Pos + getDiceSum(diceCombination);
                while (newP1Pos > 10) {
                    newP1Pos -= 10;
                }
                newP1Score = p1Score + newP1Pos;
            } else {
                newP2Pos = p2Pos + getDiceSum(diceCombination);
                while (newP2Pos > 10) {
                    newP2Pos -= 10;
                }
                newP2Score = p2Score + newP2Pos;
            }
            long[] newWins = getCachedCalcWins(3 - currentPlayer, newP1Pos, newP1Score, newP2Pos, newP2Score);
            p1Wins += newWins[0];
            p2Wins += newWins[1];
        }
        return new long[]{p1Wins, p2Wins};
    }

    public static long[] getCachedCalcWins(int currentPlayer, int p1Pos, int p1Score, int p2Pos, int p2Score) {
        String id = currentPlayer + "," + p1Pos + "," + p1Score + "," + p2Pos + "," + p2Score;
        long[] result = preCalcWins.get(id);
        if (result == null) {
            result = calcWins(currentPlayer, p1Pos, p1Score, p2Pos, p2Score);
            preCalcWins.put(id, result);
        }
        return result;
    }

    public static int getDiceSum(int diceCombination) {
        return diceCombination % 10 + (diceCombination / 10 % 10) + (diceCombination / 100 % 10);
    }

}
