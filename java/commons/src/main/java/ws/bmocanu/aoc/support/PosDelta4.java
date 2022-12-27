package ws.bmocanu.aoc.support;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.utils.XUtils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PosDelta4 {

    public static PosDelta4[] deltaValues = new PosDelta4[]{
        PosDelta4.fromDir4(0), PosDelta4.fromDir4(1), PosDelta4.fromDir4(2), PosDelta4.fromDir4(3)
    };

    public static int[] dirValues = new int[]{0, 1, 2, 3};

    public int deltaX;

    public int deltaY;

    public int dir4;

    // ----------------------------------------------------------------------------------------------------

    public static PosDelta4 fromDeltas(int deltaX, int deltaY) {
        PosDelta4 newDelta = new PosDelta4();
        newDelta.deltaX = deltaX;
        newDelta.deltaY = deltaY;
        newDelta.dir4 = getDir4ByPoint(deltaX, deltaY);
        return newDelta;
    }

    public static PosDelta4 fromCoordsInQuadrant(int x, int y) {
        return PosDelta4.fromDir4(getDir4ByPoint(x, y));
    }

    public static PosDelta4 fromPointInQuadrant(Point point) {
        return PosDelta4.fromCoordsInQuadrant(point.x, point.y);
    }

    public static PosDelta4 fromDir4(int dir4) {
        switch (dir4) {
            case 0:
                return PosDelta4.fromDeltas(0, -1);
            case 1:
                return PosDelta4.fromDeltas(1, 0);
            case 2:
                return PosDelta4.fromDeltas(0, 1);
            case 3:
                return PosDelta4.fromDeltas(-1, 0);
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir4);
        }
    }

    public static PosDelta4 north() {
        return fromDir4(0);
    }

    public static PosDelta4 up() {
        return fromDir4(0);
    }

    public static PosDelta4 south() {
        return fromDir4(2);
    }

    public static PosDelta4 down() {
        return fromDir4(2);
    }

    public static PosDelta4 east() {
        return fromDir4(1);
    }

    public static PosDelta4 right() {
        return fromDir4(1);
    }

    public static PosDelta4 west() {
        return fromDir4(3);
    }

    public static PosDelta4 left() {
        return fromDir4(3);
    }

    public static PosDelta4 fromStrings(String value, String strForNorth, String strForSouth, String strForWest, String strForEast) {
        if (strForNorth.equals(value)) {
            return north();
        } else if (strForSouth.equals(value)) {
            return south();
        } else if (strForWest.equals(value)) {
            return west();
        } else if (strForEast.equals(value)) {
            return east();
        } else {
            throw new IllegalArgumentException("The value [" + value + "] does not match the given coordinate strings");
        }
    }

    // ----------------------------------------------------------------------------------------------------

    public PosDelta4 rotateLeft() {
        int newDir4 = XUtils.cycleInt(dir4 - 1, 0, 3);
        PosDelta4 newDelta = fromDir4(newDir4);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        dir4 = newDir4;
        return this;
    }

    public PosDelta4 rotateRight() {
        int newDir4 = XUtils.cycleInt(dir4 + 1, 0, 3);
        PosDelta4 newDelta = fromDir4(newDir4);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        dir4 = newDir4;
        return this;
    }

    public boolean isUp() {
        return dir4 == 0;
    }

    public boolean isRight() {
        return dir4 == 1;
    }

    public boolean isDown() {
        return dir4 == 2;
    }

    public boolean isLeft() {
        return dir4 == 3;
    }

    // ----------------------------------------------------------------------------------------------------

    private static int getDir4ByPoint(int x, int y) {
        if (x == 0 && y < 0) {
            return 0;
        }
        if (x > 0 && y == 0) {
            return 1;
        }
        if (x == 0 && y > 0) {
            return 2;
        }
        if (x < 0 && y == 0) {
            return 3;
        }
        throw new IllegalArgumentException("Cannot calculate dir4 from x=[" + x + "], y=[" + y + "]");
    }

}
