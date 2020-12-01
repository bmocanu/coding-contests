package ws.bmocanu.aoc.support;

import ws.bmocanu.aoc.utils.Utils;

public class FlexCursor {

    public int x;

    public int y;

    public Point point;

    // ----------------------------------------------------------------------------------------------------

    private PointSupplier pointSupplier;

    public FlexCursor(int x, int y, PointSupplier pointSupplier) {
        this.pointSupplier = pointSupplier;
    }

    // ----------------------------------------------------------------------------------------------------

    public Point moveByDir0123(int dir) {
        Direction direction = Utils.directionDelta0123(dir);
        x = x + direction.deltaX;
        y = y + direction.deltaY;
        point = pointSupplier.pointOrNull(x, y);
        return point;
    }

}
