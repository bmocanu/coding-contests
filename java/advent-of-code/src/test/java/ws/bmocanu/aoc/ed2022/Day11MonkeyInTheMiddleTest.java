package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day11MonkeyInTheMiddleTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day11MonkeyInTheMiddle.setForcedFilePath("/test-ed2022/day11.txt");
    }

    @Test
    public void testCode() {
        Day11MonkeyInTheMiddle.main(null);
        assertThat(Log.getPart1Long(), is(120384L));
        assertThat(Log.getPart2Long(), is(32059801242L));
    }

}
