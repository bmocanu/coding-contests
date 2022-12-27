package ws.bmocanu.aoc.flex;

import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDelta8;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Point {

    public int x;

    public int y;

    public int newX;

    public int newY;

    public int type;

    public int value;

    public char chr;

    public String name;

    public boolean enabled;

    public boolean destroyed;

    public boolean marked;

    public boolean visited;

    public int trailMarkCount;

    public Point link;

    public int pathCount;

    public Point pathLink;
    
    public PosDelta4 dir4;

    public Map<String, Integer> trails = new HashMap<>();

    public Map<Integer, Boolean> flags = new HashMap<>();

    // ----------------------------------------------------------------------------------------------------

    public static Point from(int x, int y) {
        Point newPoint = new Point();
        newPoint.x = x;
        newPoint.y = y;
        return newPoint;
    }

    // ----------------------------------------------------------------------------------------------------

    public int taxicabDistanceTo(Point point2) {
        return taxicabDistanceTo(point2.x, point2.y);
    }

    public int taxicabDistanceTo(int otherX, int otherY) {
        return Math.abs(x - otherX) + Math.abs(y - otherY);
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

    public Point incX(int value) {
        x += value;
        return this;
    }

    public Point incY(int value) {
        y += value;
        return this;
    }

    public Point setName(String name) {
        this.name = name;
        return this;
    }

    public Point setChr(char chr) {
        this.chr = chr;
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

    public Point setPathLink(Point link) {
        this.pathLink = link;
        return this;
    }

    public Point setPathCount(int count) {
        this.pathCount = count;
        return this;
    }

    public boolean hasFlag(int flag) {
        return this.flags.getOrDefault(flag, false);
    }

    public Point setFlag(int flag) {
        this.flags.put(flag, true);
        return this;
    }

    public Point clearFlag(int flag) {
        this.flags.remove(flag);
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

    public Point unmark() {
        this.marked = false;
        return this;
    }

    public Point visit() {
        this.visited = true;
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

    public Point getOtherPointByDelta(PosDelta8 delta) {
        return Point.from(this.x + delta.deltaX, this.y + delta.deltaY);
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean isNotAt(int x, int y) {
        return this.x != x || this.y != y;
    }

    public Point deepClone() {
        Point newPoint = new Point();
        newPoint.x = x;
        newPoint.y = y;
        newPoint.type = type;
        newPoint.chr = chr;
        newPoint.value = value;
        newPoint.name = name;
        newPoint.enabled = enabled;
        newPoint.destroyed = destroyed;
        newPoint.marked = marked;
        newPoint.trailMarkCount = trailMarkCount;
        newPoint.pathCount = pathCount;
        // warning: this does not set link and pathLink, as would be just shallow clones. This must be done by hand
        return newPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
