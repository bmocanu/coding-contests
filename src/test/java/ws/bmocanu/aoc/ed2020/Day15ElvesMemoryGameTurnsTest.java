package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day15ElvesMemoryGameTurnsTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day15ElvesMemoryGameTurns.setForcedFilePath("/test-ed2020/day15.txt");
    }

    @Test
    public void testCode() {
        Day15ElvesMemoryGameTurns.main(null);
        assertThat(Log.getPart1Int(), is(496));
        assertThat(Log.getPart2Int(), is(883));
    }

}
