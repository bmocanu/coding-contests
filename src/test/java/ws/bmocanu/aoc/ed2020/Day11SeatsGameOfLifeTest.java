package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day11SeatsGameOfLifeTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day11SeatsGameOfLife.setForcedFilePath("/test-ed2020/day11.txt");
    }

    @Test
    public void testCode() {
        Day11SeatsGameOfLife.main(null);
        assertThat(Log.getPart1Int(), is(2263));
        assertThat(Log.getPart2Int(), is(2002));
    }

}
