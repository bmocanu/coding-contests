package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day13DistressSignalTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day13DistressSignal.setForcedFilePath("/test-ed2022/day13.txt");
    }

    @Test
    public void testCode() {
        Day13DistressSignal.main(null);
        assertThat(Log.getPart1Int(), is(6420));
        assertThat(Log.getPart2Int(), is(22000));
    }

}
