package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day10JoltsAndAdaptersTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day10JoltsAndAdapters.setForcedFilePath("/test-ed2020/day10.txt");
    }

    @Test
    public void testCode() {
        Day10JoltsAndAdapters.main(null);
        assertThat(Log.getPart1Int(), is(2070));
        assertThat(Log.getPart2Long(), is(24179327893504L));
    }

}
