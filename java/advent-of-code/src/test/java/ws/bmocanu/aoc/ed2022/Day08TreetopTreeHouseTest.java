package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day08TreetopTreeHouseTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day08TreetopTreeHouse.setForcedFilePath("/test-ed2022/day08.txt");
    }

    @Test
    public void testCode() {
        Day08TreetopTreeHouse.main(null);
        assertThat(Log.getPart1Int(), is(1698));
        assertThat(Log.getPart2Int(), is(672280));
    }

}
