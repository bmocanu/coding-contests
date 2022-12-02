package ws.bmocanu.aoc.ed2015;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day07BitwiseLogicGatesTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day07BitwiseLogicGates.setForcedFilePath("/test-ed2015/day07.txt");
    }

    @Test
    public void testCode() {
        Day07BitwiseLogicGates.main(null);
        assertThat(Log.getPart1Int(), is(956));
        assertThat(Log.getPart2Int(), is(40149));
    }

}
