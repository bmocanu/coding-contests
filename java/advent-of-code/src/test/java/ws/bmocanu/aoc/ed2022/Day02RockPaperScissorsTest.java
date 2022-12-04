package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day02RockPaperScissorsTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day02RockPaperScissors.setForcedFilePath("/test-ed2022/day02.txt");
    }

    @Test
    public void testCode() {
        Day02RockPaperScissors.main(null);
        assertThat(Log.getPart1Int(), is(11063));
        assertThat(Log.getPart2Int(), is(10349));
    }

}
