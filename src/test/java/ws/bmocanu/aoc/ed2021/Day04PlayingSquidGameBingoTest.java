package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day04PlayingSquidGameBingoTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day04PlayingSquidGameBingo.setForcedFilePath("/test-ed2021/day04.txt");
    }

    @Test
    public void testCode() {
        Day04PlayingSquidGameBingo.main(null);
        assertThat(Log.getPart1Int(), is(25410));
        assertThat(Log.getPart2Int(), is(2730));
    }

}
