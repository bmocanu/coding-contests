package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day07CrabSubmarinesFuelTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day07CrabSubmarinesFuel.setForcedFilePath("/test-ed2021/day07.txt");
    }

    @Test
    public void testCode() {
        Day07CrabSubmarinesFuel.main(null);
        assertThat(Log.getPart1Int(), is(331067));
        assertThat(Log.getPart2Int(), is(92881128));
    }

}
