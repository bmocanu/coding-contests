package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day06QuestionAnsweredYesTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
    }

    @Test
    public void testCode() {
        Day06QuestionAnsweredYes.main(null);
        assertThat(Log.getPart1Int(), is(6259));
        assertThat(Log.getPart2Int(), is(3178));
    }

}
