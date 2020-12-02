package ws.bmocanu.aoc.path;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class PathMarker {

    private final FlexStruct struct;
    private final PathAdvisor advisor;

    // ----------------------------------------------------------------------------------------------------

    public PathMarker(FlexStruct struct, PathAdvisor advisor) {
        this.struct = struct;
        this.advisor = advisor;
    }

    // ----------------------------------------------------------------------------------------------------

    public void startFrom(int x, int y) {
        startFrom(Point.from(x, y));
    }

    public void startFrom(Point startPoint) {
        struct.forAllPoints().setPathCount(-1);
        struct.point(startPoint).pathCount = 0;
        int pathCount = 0;
        boolean stillOneCellWalked = true;
        while (stillOneCellWalked) {
            stillOneCellWalked = false;
            for (Point point : struct.allPoints()) {
                if (point.pathCount == pathCount) {
                    stillOneCellWalked |= processPointAndReturnIfWalked(point, pathCount);
                }
            }
            pathCount++;
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private boolean processPointAndReturnIfWalked(Point point, int currentPathCount) {
        boolean atLeastOnePointWalked = false;
        for (int dir = 0; dir < 4; dir++) {
            Point otherPoint = struct.pointOrNull(point, Utils.directionDelta0123(dir));
            if (otherPoint != null && otherPoint.pathCount == -1 && advisor.isFreeToWalk(otherPoint)) {
                otherPoint.pathCount = currentPathCount + 1;
                atLeastOnePointWalked = true;
            }
        }
        return atLeastOnePointWalked;
    }

}
