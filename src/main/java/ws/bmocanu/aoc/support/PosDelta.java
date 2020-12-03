package ws.bmocanu.aoc.support;

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

    public static PosDelta byDir03(int dir) {
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

    public static PosDelta byDir07(int dir) {
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

}
