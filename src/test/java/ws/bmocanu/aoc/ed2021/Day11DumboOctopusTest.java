package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day11DumboOctopusTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day11DumboOctopus.setForcedFilePath("/test-ed2021/day11.txt");
    }

    @Test
    public void testCode() {
        Day11DumboOctopus.main(null);
        assertThat(Log.getPart1Int(), is(1667));
        assertThat(Log.getPart2Int(), is(488));
    }

}
