package ws.bmocanu.aoc.utils;

public class Direction {

    public int deltaX;

    public int deltaY;

    // ----------------------------------------------------------------------------------------------------

    public static Direction from(int deltaX, int deltaY) {
        Direction newDir = new Direction();
        newDir.deltaX = deltaX;
        newDir.deltaY = deltaY;
        return newDir;
    }

}
