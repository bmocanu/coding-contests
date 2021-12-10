package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day10SyntaxScoringTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day10SyntaxScoring.setForcedFilePath("/test-ed2021/day10.txt");
    }

    @Test
    public void testCode() {
        Day10SyntaxScoring.main(null);
        assertThat(Log.getPart1Int(), is(392139));
        assertThat(Log.getPart2Long(), is(4001832844L));
    }

}