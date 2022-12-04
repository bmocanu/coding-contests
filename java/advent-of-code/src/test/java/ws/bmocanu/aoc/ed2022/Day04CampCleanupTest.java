package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day04CampCleanupTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day04CampCleanup.setForcedFilePath("/test-ed2022/day04.txt");
    }

    @Test
    public void testCode() {
        Day04CampCleanup.main(null);
        assertThat(Log.getPart1Int(), is(511));
        assertThat(Log.getPart2Int(), is(821));
    }

}
