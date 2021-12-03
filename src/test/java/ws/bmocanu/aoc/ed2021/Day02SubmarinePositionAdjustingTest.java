package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day02SubmarinePositionAdjustingTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day02SubmarinePositionAdjusting.setForcedFilePath("/test-ed2021/day02.txt");
    }

    @Test
    public void testCode() {
        Day02SubmarinePositionAdjusting.main(null);
        assertThat(Log.getPart1Int(), is(1815044));
        assertThat(Log.getPart2Int(), is(1739283308));
    }

}
