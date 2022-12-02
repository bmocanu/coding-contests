package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day22RecursiveCrabCombatTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day22RecursiveCrabCombat.setForcedFilePath("/test-ed2020/day22.txt");
    }

    @Test
    public void testCode() {
        Day22RecursiveCrabCombat.main(null);
        assertThat(Log.getPart1Int(), is(30138));
        assertThat(Log.getPart2Int(), is(31587));
    }

}
