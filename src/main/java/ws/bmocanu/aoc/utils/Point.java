package ws.bmocanu.aoc.utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Point {

    public int x;

    public int y;

    public int type;

    public int value;

    public char character;

    public String name;

    public boolean enabled;

    public boolean destroyed;

    public boolean marked;

    public int trailMarkCount;

    public Point link;

    // ----------------------------------------------------------------------------------------------------

    public static Point from(int x, int y) {
        Point newPoint = new Point();
        newPoint.x = x;
        newPoint.y = y;
        return newPoint;
    }

    // ----------------------------------------------------------------------------------------------------

    public int taxicabDistanceTo(Point point2) {
        return Math.abs(x - point2.x) + Math.abs(y - point2.y);
    }

    // ----------------------------------------------------------------------------------------------------

    public Point setValue(int value) {
        this.value = value;
        return this;
    }

    public Point setX(int x) {
        this.x = x;
        return this;
    }

    public Point setY(int y) {
        this.y = y;
        return this;
    }

    public Point setName(String name) {
        this.name = name;
        return this;
    }

    public Point setCharacter(char character) {
        this.character = character;
        return this;
    }

    public Point setTrailMarkCount(int trailMarkCount) {
        this.trailMarkCount = trailMarkCount;
        return this;
    }

    public Point setLink(Point link) {
        this.link = link;
        return this;
    }

    public Point trailMark() {
        this.trailMarkCount++;
        return this;
    }

    public Point mark() {
        this.marked = true;
        return this;
    }

    public Point enable() {
        this.enabled = true;
        return this;
    }

    public Point disable() {
        this.enabled = false;
        return this;
    }

    public Point destroy() {
        this.destroyed = true;
        return this;
    }

    public Point setType(int type) {
        this.type = type;
        return this;
    }

    public Point deepClone() {
        Point newPoint = new Point();
        newPoint.x = x;
        newPoint.y = y;
        newPoint.type = type;
        newPoint.value = value;
        newPoint.name = name;
        newPoint.enabled = enabled;
        newPoint.destroyed = destroyed;
        newPoint.marked = marked;
        newPoint.trailMarkCount = trailMarkCount;
        return newPoint;
    }

}
