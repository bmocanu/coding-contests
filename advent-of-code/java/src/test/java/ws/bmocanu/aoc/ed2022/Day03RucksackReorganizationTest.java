package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day03RucksackReorganizationTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day03RucksackReorganization.setForcedFilePath("/test-ed2022/day03.txt");
    }

    @Test
    public void testCode() {
        Day03RucksackReorganization.main(null);
        assertThat(Log.getPart1Int(), is(8039));
        assertThat(Log.getPart2Int(), is(2510));
    }

}
