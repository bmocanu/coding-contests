package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day08GameConsoleCodeTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
    }

    @Test
    public void testCode() {
        Day08GameConsoleCode.main(null);
        assertThat(Log.getPart1Int(), is(1394));
        assertThat(Log.getPart2Int(), is(1626));
    }

}
