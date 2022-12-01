package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day01CalorieCountingTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day01CalorieCounting.setForcedFilePath("/test-ed2022/day01.txt");
    }

    @Test
    public void testCode() {
        Day01CalorieCounting.main(null);
        assertThat(Log.getPart1Int(), is(70374));
        assertThat(Log.getPart2Int(), is(204610));
    }

}
