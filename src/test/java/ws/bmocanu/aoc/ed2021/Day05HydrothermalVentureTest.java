package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day05HydrothermalVentureTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day05HydrothermalVenture.setForcedFilePath("/test-ed2021/day05.txt");
    }

    @Test
    public void testCode() {
        Day05HydrothermalVenture.main(null);
        assertThat(Log.getPart1Int(), is(5147));
        assertThat(Log.getPart2Int(), is(16925));
    }

}
