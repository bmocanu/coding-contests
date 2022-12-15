package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day15BeaconExclusionZoneTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day15BeaconExclusionZone.setForcedFilePath("/test-ed2022/day15.txt");
    }

    @Test
    public void testCode() {
        Day15BeaconExclusionZone.main(null);
        assertThat(Log.getPart1Int(), is(4883971));
        assertThat(Log.getPart2Long(), is(12691026767556L));
    }

}
