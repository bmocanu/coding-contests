package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day06TuningTroubleTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day06TuningTrouble.setForcedFilePath("/test-ed2022/day06.txt");
    }

    @Test
    public void testCode() {
        Day06TuningTrouble.main(null);
        assertThat(Log.getPart1Int(), is(1987));
        assertThat(Log.getPart2Int(), is(3059));
    }

}
