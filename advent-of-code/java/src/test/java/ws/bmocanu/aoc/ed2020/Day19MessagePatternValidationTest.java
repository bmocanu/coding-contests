package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day19MessagePatternValidationTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day19MessagePatternValidation.setForcedFilePath("/test-ed2020/day19.txt");
    }

    @Test
    public void testCode() {
        Day19MessagePatternValidation.main(null);
        assertThat(Log.getPart1Int(), is(195));
        assertThat(Log.getPart2Int(), is(309));
    }

}
