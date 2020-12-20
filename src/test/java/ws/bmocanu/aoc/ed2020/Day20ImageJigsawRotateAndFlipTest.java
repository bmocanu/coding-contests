package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day20ImageJigsawRotateAndFlipTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day20ImageJigsawRotateAndFlip.setForcedFilePath("/test-ed2020/day20.txt");
    }

    @Test
    public void testCode() {
        Day20ImageJigsawRotateAndFlip.main(null);
        assertThat(Log.getPart1Long(), is(20913499394191L));
        assertThat(Log.getPart2Long(), is(2209L));
    }

}
