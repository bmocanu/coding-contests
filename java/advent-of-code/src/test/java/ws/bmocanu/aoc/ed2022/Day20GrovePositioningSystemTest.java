package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day20GrovePositioningSystemTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day20GrovePositioningSystem.setForcedFilePath("/test-ed2022/day20.txt");
    }

    @Test
    public void testCode() {
        Day20GrovePositioningSystem.main(null);
        assertThat(Log.getPart1Long(), is(3L));
        assertThat(Log.getPart2Long(), is(1623178306L));
    }

}
