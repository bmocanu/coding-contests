package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day14RegolithReservoirTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day14RegolithReservoir.setForcedFilePath("/test-ed2022/day14.txt");
    }

    @Test
    public void testCode() {
        Day14RegolithReservoir.main(null);
        assertThat(Log.getPart1Int(), is(873));
        assertThat(Log.getPart2Int(), is(24813));
    }

}
