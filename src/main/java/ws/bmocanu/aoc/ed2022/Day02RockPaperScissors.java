package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.Arrays;
import java.util.List;

public class Day02RockPaperScissors extends SolutionBase {

    public static void main(String[] args) {
        SELECTION.init();
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day02"));
        int totalScore = 0;
        int totalScore2 = 0;
        for (String line : input) {
            SELECTION op = SELECTION.getByChar(line.substring(0, 1));
            SELECTION you = SELECTION.getByChar(line.substring(2, 3));

            totalScore += you.score + scoreForMatch(op, you);

            SELECTION calculatedYou = selectionForScore(op, you.score);
            totalScore2 += calculatedYou.score + scoreForMatch(op, calculatedYou);
        }

        Log.part1(totalScore);
        Log.part2(totalScore2);
    }

    // ----------------------------------------------------------------------------------------------------

    public static int scoreForMatch(SELECTION op, SELECTION you) {
        if (op == you) {
            return 3;
        } else if (op == you.beater) {
            return 0;
        } else {
            return 6;
        }
    }

    public static SELECTION selectionForScore(SELECTION op, int desiredScore) {
        switch (desiredScore) {
            case 1:
                return op.beats;
            case 2:
                return op;
            case 3:
                return op.beater;
        }
        throw new IllegalArgumentException("Failed");
    }

    // ----------------------------------------------------------------------------------------------------

    private enum SELECTION {
        ROCK,
        PAPER,
        SCISSORS;

        public int score;
        public SELECTION beater;
        public SELECTION beats;
        public String chars;

        public static void init() {
            ROCK.score = 1;
            ROCK.beater = PAPER;
            ROCK.beats = SCISSORS;
            ROCK.chars = "AX";
            PAPER.score = 2;
            PAPER.beater = SCISSORS;
            PAPER.beats = ROCK;
            PAPER.chars = "BY";
            SCISSORS.score = 3;
            SCISSORS.beater = ROCK;
            SCISSORS.beats = PAPER;
            SCISSORS.chars = "CZ";
        }

        public static SELECTION getByChar(String ch) {
            return Arrays.stream(values()).filter(sel -> sel.chars.contains(ch)).findFirst().orElseThrow();
        }
    }

}
