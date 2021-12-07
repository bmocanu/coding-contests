package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day06LanternfishCountTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day06LanternfishCount.setForcedFilePath("/test-ed2021/day06.txt");
    }

    @Test
    public void testCode() {
        Day06LanternfishCount.main(null);
        assertThat(Log.getPart1Long(), is(395627L));
        assertThat(Log.getPart2Long(), is(1767323539209L));
    }

}
