package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ws.bmocanu.aoc.support.PosDelta4.*;

public class Day17PyroclasticFlow extends SolutionBase {

    private static final int ROCK = 1;
    private static final Map<String, BoardInstant> boardState = new HashMap<>();
    private static final long PART2_TARGET_ROCK_COUNT = 1000000000000L;
    private static int pieceType = -1;
    private static int pushIndex = -1;
    private static String pushes;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day17"));
        pushes = input.get(0);
        FlexStruct flex = new FlexStruct();

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 10; y++) {
                flex.point(x, y);
            }
        }

        for (int rockIndex = 0; rockIndex < 10000; rockIndex++) {
            if (rockIndex == 2022) {
                Log.part1(flex.highestYForPointsOfType(ROCK) + 1);
            }
            pieceType = (pieceType + 1) % 5;
            int minStartY = flex.highestYForPointsOfType(ROCK);
            minStartY += (rockIndex == 0 ? 3 : 4) + getPieceHeight(pieceType);
            Piece piece = getPiece(pieceType, minStartY);
            boolean pieceMoved = true;
            while (pieceMoved) {
                pieceMoved = false;
                PosDelta4 up = up();
                if (pieceCanMove(piece, up, flex)) {
                    piece.move(up);
                    pieceMoved = true;
                    pushIndex = (pushIndex + 1) % pushes.length();
                    char push = getPush(pushIndex);
                    // directions are reversed, as we are not falling things, but pushing them up
                    PosDelta4 pushDir = (push == '>') ? left() : right();
                    if (pieceCanMove(piece, pushDir, flex)) {
                        piece.move(pushDir);
                    }
                } else {
                    for (Point piecePoint : piece.points) {
                        flex.point(piecePoint).type = ROCK;
                    }
                    if (checkRepetitiveBoardState(flex,
                            (pieceType + 1) % 5,
                            (pushIndex + 1) % pushes.length(),
                            rockIndex)) {
                        return;
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private static boolean checkRepetitiveBoardState(FlexStruct flex, int pieceType, int pushIndex, int rockIndex) {
        rockIndex = rockIndex + 1;
        StringBuilder builder = new StringBuilder(30);
        int[] towerPeaks = new int[7];
        for (int x = 0; x < 7; x++) {
            towerPeaks[x] = flex.highestYForXAndPointsOfType(x, ROCK);
        }
        int minValue = XUtils.minFromArray(towerPeaks).value;
        int towerHeight = XUtils.maxFromArray(towerPeaks).value;
        for (int x = 0; x < 7; x++) {
            builder.append(towerPeaks[x] - minValue).append(',');
        }
        builder.append(pieceType).append(',');
        builder.append(pushIndex).append(',');
        String state = builder.toString();
        BoardInstant previousInstant = boardState.get(state);
        if (previousInstant == null) {
            boardState.put(state, new BoardInstant(rockIndex, towerHeight));
        } else {
            if ((PART2_TARGET_ROCK_COUNT - previousInstant.rockIndex) %
                    (rockIndex - previousInstant.rockIndex) == 0) {
                Log.part2((PART2_TARGET_ROCK_COUNT - previousInstant.rockIndex) /
                        (rockIndex - previousInstant.rockIndex) *
                        (towerHeight - previousInstant.towerHeight) +
                        (previousInstant.towerHeight + 1));
                return true;
            }
        }
        return false;
    }

    private static class Piece {
        List<Point> points;
        int height;

        public Piece(List<Point> points, int height) {
            this.points = points;
            this.height = height;
        }

        public void move(PosDelta4 dir) {
            for (Point p : points) {
                p.x += dir.deltaX;
                p.y += dir.deltaY;
            }
        }
    }

    private static class BoardInstant {
        int rockIndex;
        int towerHeight;

        public BoardInstant(int rockIndex, int towerHeight) {
            this.rockIndex = rockIndex;
            this.towerHeight = towerHeight;
        }
    }

    private static boolean pieceCanMove(Piece piece, PosDelta4 dir, FlexStruct flex) {
        for (Point piecePoint : piece.points) {
            Point cavePoint = flex.point(piecePoint, dir);
            if (cavePoint.x < 0 || cavePoint.x > 6 || cavePoint.y < 0 || cavePoint.type != 0) {
                return false;
            }
        }
        return true;
    }

    private static char getPush(int pushIndex) {
        return pushes.charAt(pushIndex);
    }

    private static Piece getPiece(int pieceType, int startY) {
        switch (pieceType) {
            case 0: // -
                return new Piece(
                        Arrays.asList(
                                Point.from(1, startY),
                                Point.from(2, startY),
                                Point.from(3, startY),
                                Point.from(4, startY)
                        ), 1
                );
            case 1: // +
                return new Piece(
                        Arrays.asList(
                                Point.from(3, startY),
                                Point.from(2, startY - 1),
                                Point.from(3, startY - 1),
                                Point.from(4, startY - 1),
                                Point.from(3, startY - 2)
                        ), 3
                );
            case 2: // L
                return new Piece(
                        Arrays.asList(
                                Point.from(2, startY),
                                Point.from(2, startY - 1),
                                Point.from(2, startY - 2),
                                Point.from(3, startY - 2),
                                Point.from(4, startY - 2)
                        ), 3
                );
            case 3: // |
                return new Piece(
                        Arrays.asList(
                                Point.from(4, startY),
                                Point.from(4, startY - 1),
                                Point.from(4, startY - 2),
                                Point.from(4, startY - 3)
                        ), 4
                );
            case 4: // ::
                return new Piece(
                        Arrays.asList(
                                Point.from(3, startY),
                                Point.from(4, startY),
                                Point.from(3, startY - 1),
                                Point.from(4, startY - 1)
                        ), 2
                );
            default:
                throw new IllegalArgumentException("Invalid pieceTypeIndex = " + pieceType);
        }

    }

    private static int getPieceHeight(int pieceType) {
        switch (pieceType) {
            case 0: // -
                return 1;
            case 1: // +
                return 3;
            case 2: // L
                return 3;
            case 3: // |
                return 4;
            case 4: // ::
                return 2;
            default:
                throw new IllegalArgumentException("Invalid pieceTypeIndex = " + pieceType);
        }

    }

}
