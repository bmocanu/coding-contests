package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day18BoilingBouldersTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day18BoilingBoulders.setForcedFilePath("/test-ed2022/day18.txt");
    }

    @Test
    public void testCode() {
        Day18BoilingBoulders.main(null);
        assertThat(Log.getPart1Int(), is(4418));
        assertThat(Log.getPart2Int(), is(2486));
    }

}
