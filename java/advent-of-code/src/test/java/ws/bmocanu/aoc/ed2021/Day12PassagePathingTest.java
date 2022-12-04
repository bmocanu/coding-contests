package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day12PassagePathingTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day12PassagePathing.setForcedFilePath("/test-ed2021/day12.txt");
    }

    @Test
    public void testCode() {
        Day12PassagePathing.main(null);
        assertThat(Log.getPart1Int(), is(3856));
        assertThat(Log.getPart2Int(), is(116692));
    }

}
