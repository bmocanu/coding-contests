package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day08SevenSegmentSearchTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day08SevenSegmentSearch.setForcedFilePath("/test-ed2021/day08.txt");
    }

    @Test
    public void testCode() {
        Day08SevenSegmentSearch.main(null);
        assertThat(Log.getPart1Int(), is(301));
        assertThat(Log.getPart2Int(), is(908067));
    }

}
