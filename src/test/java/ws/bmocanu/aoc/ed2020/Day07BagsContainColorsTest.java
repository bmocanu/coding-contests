package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day07BagsContainColorsTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day07BagsContainColors.setForcedFilePath("/test-ed2020/day07.txt");
    }

    @Test
    public void testCode() {
        Day07BagsContainColors.main(null);
        assertThat(Log.getPart1Int(), is(378));
        assertThat(Log.getPart2Int(), is(27526));
    }

}
