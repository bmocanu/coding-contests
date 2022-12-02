package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.xbase.SolutionBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day12ShipMovingToWaypointTest extends SolutionBase {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day12ShipMovingToWaypoint.setForcedFilePath("/test-ed2020/day12.txt");
    }

    @Test
    public void testCode() {
        Day12ShipMovingToWaypoint.main(null);
        assertThat(Log.getPart1Int(), is(879));
        assertThat(Log.getPart2Int(), is(18107));
    }

}
