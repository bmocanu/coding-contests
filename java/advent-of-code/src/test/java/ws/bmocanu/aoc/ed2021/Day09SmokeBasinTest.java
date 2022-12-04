package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day09SmokeBasinTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day09SmokeBasin.setForcedFilePath("/test-ed2021/day09.txt");
    }

    @Test
    public void testCode() {
        Day09SmokeBasin.main(null);
        assertThat(Log.getPart1Int(), is(562));
        assertThat(Log.getPart2Int(), is(1076922));
    }

}
