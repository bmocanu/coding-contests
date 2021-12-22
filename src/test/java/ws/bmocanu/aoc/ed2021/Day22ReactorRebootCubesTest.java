package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day22ReactorRebootCubesTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day22ReactorRebootCubes.setForcedFilePath("/test-ed2021/day22.txt");
    }

    @Test
    public void testCode() {
        Day22ReactorRebootCubes.main(null);
        assertThat(Log.getPart1Long(), is(609563L));
        assertThat(Log.getPart2Long(), is(1234650223944734L));
    }

}
