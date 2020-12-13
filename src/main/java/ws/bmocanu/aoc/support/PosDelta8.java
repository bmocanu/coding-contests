package ws.bmocanu.aoc.support;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PosDelta8 {

    public static PosDelta8[] deltaValues = new PosDelta8[]{
            PosDelta8.fromDir8(0), PosDelta8.fromDir8(1), PosDelta8.fromDir8(2), PosDelta8.fromDir8(3),
            PosDelta8.fromDir8(4), PosDelta8.fromDir8(5), PosDelta8.fromDir8(6), PosDelta8.fromDir8(7)
    };

    public static int[] dirValues = new int[]{0, 1, 2, 3, 4, 5, 6, 7};

    public int deltaX;

    public int deltaY;

    public int dir8;

    // ----------------------------------------------------------------------------------------------------

    public static PosDelta8 fromDeltas(int deltaX, int deltaY) {
        PosDelta8 newDelta = new PosDelta8();
        newDelta.deltaX = deltaX;
        newDelta.deltaY = deltaY;
        newDelta.dir8 = getDir8ByPoint(deltaX, deltaY);
        return newDelta;
    }

    public static PosDelta8 fromCoordsInQuadrant(int x, int y) {
        return PosDelta8.fromDir8(getDir8ByPoint(x, y));
    }

    public static PosDelta8 fromPointInQuadrant(Point point) {
        return PosDelta8.fromCoordsInQuadrant(point.x, point.y);
    }

    public static PosDelta8 fromDir8(int dir) {
        switch (dir) {
            case 0:
                return PosDelta8.fromDeltas(0, -1);
            case 1:
                return PosDelta8.fromDeltas(1, -1);
            case 2:
                return PosDelta8.fromDeltas(1, 0);
            case 3:
                return PosDelta8.fromDeltas(1, 1);
            case 4:
                return PosDelta8.fromDeltas(0, 1);
            case 5:
                return PosDelta8.fromDeltas(-1, 1);
            case 6:
                return PosDelta8.fromDeltas(-1, 0);
            case 7:
                return PosDelta8.fromDeltas(-1, -1);
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    // ----------------------------------------------------------------------------------------------------

    public PosDelta8 rotateLeft() {
        int newDir8 = Utils.cycleInt(dir8 - 1, 0, 7);
        PosDelta8 newDelta = fromDir8(newDir8);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        dir8 = newDir8;
        return this;
    }

    public PosDelta8 rotateRight() {
        int newDir8 = Utils.cycleInt(dir8 + 1, 0, 7);
        PosDelta8 newDelta = fromDir8(newDir8);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        dir8 = newDir8;
        return this;
    }

    // ----------------------------------------------------------------------------------------------------

    private static int getDir8ByPoint(int x, int y) {
        if (x == 0 && y < 0) {
            return 0;
        }
        if (x == 0 && y > 0) {
            return 4;
        }
        if (x < 0 && y == 0) {
            return 6;
        }
        if (x > 0 && y == 0) {
            return 2;
        }
        if (x > 0 && y < 0) {
            return 1;
        }
        if (x < 0 && y > 0) {
            return 5;
        }
        if (x > 0) {
            return 3;
        }
        if (x < 0) {
            return 7;
        }
        throw new IllegalArgumentException("Cannot calculate dir8 from x=[" + x + "], y=[" + y + "]");
    }

}
