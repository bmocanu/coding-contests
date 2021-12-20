package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day20TrenchMapImageEnhancementTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day20TrenchMapImageEnhancement.setForcedFilePath("/test-ed2021/day20.txt");
    }

    @Test
    public void testCode() {
        Day20TrenchMapImageEnhancement.main(null);
        assertThat(Log.getPart1Int(), is(5339));
        assertThat(Log.getPart2Int(), is(18395));
    }

}
