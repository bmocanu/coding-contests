package ws.bmocanu.aoc.support;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PosDelta {

    public int deltaX;

    public int deltaY;

    // ----------------------------------------------------------------------------------------------------

    public static PosDelta from(int deltaX, int deltaY) {
        PosDelta newDelta = new PosDelta();
        newDelta.deltaX = deltaX;
        newDelta.deltaY = deltaY;
        return newDelta;
    }

    public static PosDelta byDir4(int dir) {
        switch (dir) {
            case 0:
                return PosDelta.from(0, -1);
            case 1:
                return PosDelta.from(1, 0);
            case 2:
                return PosDelta.from(0, 1);
            case 3:
                return PosDelta.from(-1, 0);
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    public static PosDelta byDir8(int dir) {
        switch (dir) {
            case 0:
                return PosDelta.from(0, -1);
            case 1:
                return PosDelta.from(1, -1);
            case 2:
                return PosDelta.from(1, 0);
            case 3:
                return PosDelta.from(1, 1);
            case 4:
                return PosDelta.from(0, 1);
            case 5:
                return PosDelta.from(-1, 1);
            case 6:
                return PosDelta.from(-1, 0);
            case 7:
                return PosDelta.from(-1, -1);
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    public static int getDir8ByPoint(Point point) {
        return getDir8ByPoint(point.x, point.y);
    }

    public static int getDir8ByDelta(PosDelta delta) {
        return getDir8ByPoint(delta.deltaX, delta.deltaY);
    }

    public static int getDir8ByPoint(int x, int y) {
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
        return 0;
    }

    public static PosDelta byPoint(int x, int y) {
        return PosDelta.byDir8(getDir8ByPoint(x, y));
    }

    public static PosDelta byPoint(Point point) {
        return PosDelta.byDir8(getDir8ByPoint(point));
    }

    // ----------------------------------------------------------------------------------------------------

    public PosDelta rotateLeft() {
        int dir = getDir8ByDelta(this);
        int newDir = Utils.cycleInt(dir - 1, 0, 7);
        PosDelta newDelta = byDir8(newDir);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        return this;
    }

    public PosDelta rotateRight() {
        int dir = getDir8ByDelta(this);
        int newDir = Utils.cycleInt(dir + 1, 0, 7);
        PosDelta newDelta = byDir8(newDir);
        deltaX = newDelta.deltaX;
        deltaY = newDelta.deltaY;
        return this;
    }

}
