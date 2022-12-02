package ws.bmocanu.aoc.flex;

import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDeltaDiff;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Cursor {

    public int x;

    public int y;

    public Point point;

    public boolean cyclingOnX;

    public boolean cyclingOnY;

    private int targetX;

    private int targetY;

    private boolean createPointsAsNeeded;

    // ----------------------------------------------------------------------------------------------------

    private final PointSupplier pointSupplier;

    public Cursor(int x, int y, PointSupplier pointSupplier) {
        this.pointSupplier = pointSupplier;
        this.x = x;
        this.y = y;
        this.point = pointSupplier.pointOrNull(x, y);
    }

    // ----------------------------------------------------------------------------------------------------

    public Cursor enableCycling(boolean onX, boolean onY) {
        cyclingOnX = onX;
        cyclingOnY = onY;
        return this;
    }

    public Cursor createPointsAsNeeded() {
        createPointsAsNeeded = true;
        updatePointReference();
        return this;
    }

    public Point moveByDir4(int dir) {
        PosDelta4 posDelta = PosDelta4.fromDir4(dir);
        x = x + posDelta.deltaX;
        y = y + posDelta.deltaY;
        updatePointReference();
        return point;
    }

    public Point moveBy(int deltaX, int deltaY) {
        x = x + deltaX;
        y = y + deltaY;
        updatePointReference();
        return point;
    }

    public void setTarget(int x, int y) {
        targetX = x;
        targetY = y;
    }

    public Point moveTowardsTarget() {
        int deltaX = PosDeltaDiff.getDeltaBetween(this.x, targetX);
        int deltaY = PosDeltaDiff.getDeltaBetween(this.y, targetY);
        x = x + deltaX;
        y = y + deltaY;
        updatePointReference();
        return point;
    }

    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean isNotAt(int x, int y) {
        return this.x != x || this.y != y;
    }

    public boolean isNotAtTarget() {
        return isNotAt(targetX, targetY);
    }

    // ----------------------------------------------------------------------------------------------------

    private void updatePointReference() {
        if (cyclingOnX) {
            if (x >= pointSupplier.width()) {
                x = x - pointSupplier.width();
            }
        }
        if (cyclingOnY) {
            if (y >= pointSupplier.height()) {
                y = y - pointSupplier.height();
            }
        }
        if (createPointsAsNeeded) {
            point = pointSupplier.point(x, y);
        } else {
            point = pointSupplier.pointOrNull(x, y);
        }
    }

}
