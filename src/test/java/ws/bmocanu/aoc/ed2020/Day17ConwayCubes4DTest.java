package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day17ConwayCubes4DTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day17ConwayCubes4D.setForcedFilePath("/test-ed2020/day18.txt");
    }

    @Test
    public void testCode() {
        Day17ConwayCubes4D.main(null);
        assertThat(Log.getPart1Int(), is(401));
        assertThat(Log.getPart2Int(), is(2224));
    }

}
