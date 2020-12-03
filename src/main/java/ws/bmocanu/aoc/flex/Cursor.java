package ws.bmocanu.aoc.flex;

import ws.bmocanu.aoc.support.PosDelta;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Cursor {

    public int x;

    public int y;

    public Point point;

    public boolean cyclingOnX;

    public boolean cyclingOnY;

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

    public Point moveByDir03(int dir) {
        PosDelta posDelta = PosDelta.byDir03(dir);
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
        point = pointSupplier.pointOrNull(x, y);
    }

}
