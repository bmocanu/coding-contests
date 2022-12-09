package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day09RopeBridgeTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day09RopeBridge.setForcedFilePath("/test-ed2022/day09.txt");
    }

    @Test
    public void testCode() {
        Day09RopeBridge.main(null);
        assertThat(Log.getPart1Int(), is(6357));
        assertThat(Log.getPart2Int(), is(2627));
    }

}
