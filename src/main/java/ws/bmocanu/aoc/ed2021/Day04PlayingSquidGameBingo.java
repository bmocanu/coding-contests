package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XMatrix;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day04PlayingSquidGameBingo extends SolutionBase {

    public static class BoardNr {
        int value;
        boolean called;
    }

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day04"));
        List<Integer> numbers = XUtils.splitCsvStringToIntList(input.get(0), ",");

        List<BoardNr[][]> boards = new ArrayList<>();
        List<String> currentBoardInput = null;
        for (int index = 1; index < input.size(); index++) {
            if (input.get(index).trim().length() == 0) {
                if (currentBoardInput != null) {
                    BoardNr[][] newBoard = XMatrix.createObjectMatrix2(5, 5, BoardNr.class);
                    XMatrix.initObjectMatrixFromStringListWithInts(newBoard, currentBoardInput,
                            " ", (x, y, obj, intValue) -> {
                                obj.value = intValue;
                                obj.called = false;
                            });
                    boards.add(newBoard);
                }
                currentBoardInput = new ArrayList<>();
            } else {
                currentBoardInput.add(input.get(index));
            }
        }

        int firstScore = 0;
        int lastScore = 0;

        for (int currentNr : numbers) {
            for (Iterator<BoardNr[][]> boardIterator = boards.iterator(); boardIterator.hasNext(); ) {
                BoardNr[][] board = boardIterator.next();
                XMatrix.iterateObjectMatrix2(board, (x, y, boardNr) -> {
                    if (boardNr.value == currentNr) {
                        boardNr.called = true;
                    }
                });
                if (boardWins(board)) {
                    int score = countBoardScore(board, currentNr);
                    if (firstScore <= 0) {
                        firstScore = score;
                    }
                    lastScore = score;
                    boardIterator.remove();
                }
            }
        }

        Log.part1(firstScore);
        Log.part2(lastScore);
    }

    public static boolean boardWins(BoardNr[][] board) {
        if (XMatrix.inObjectMatrixFindLineWithAllElementsMatching(board, elem -> elem.called) >= 0) {
            return true;
        }
        if (XMatrix.inObjectMatrixFindColumnWithAllElementsMatching(board, elem -> elem.called) >= 0) {
            return true;
        }
        return false;
    }

    public static int countBoardScore(BoardNr[][] board, int lastNrCalled) {
        final AtomicInteger atomInt = new AtomicInteger();
        XMatrix.iterateObjectMatrix2(board, (x, y, boardNr) -> {
            if (!boardNr.called) {
                atomInt.addAndGet(boardNr.value);
            }
        });
        return atomInt.get() * lastNrCalled;
    }

}
