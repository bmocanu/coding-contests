package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day17PyroclasticFlowTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day17PyroclasticFlow.setForcedFilePath("/test-ed2022/day17.txt");
    }

    @Test
    public void testCode() {
        Day17PyroclasticFlow.main(null);
        assertThat(Log.getPart1Int(), is(3191));
        assertThat(Log.getPart2Long(), is(1572093023267L));
    }

}
