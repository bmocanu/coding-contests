package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day18SnailfishMathTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day18SnailfishMath.setForcedFilePath("/test-ed2021/day18.txt");
    }

    @Test
    public void testCode() {
        Day18SnailfishMath.main(null);
        assertThat(Log.getPart1Int(), is(3734));
        assertThat(Log.getPart2Int(), is(4837));
    }

}
