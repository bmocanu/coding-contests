package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day25SeaCucumberTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day25SeaCucumber.setForcedFilePath("/test-ed2021/day25.txt");
    }

    @Test
    public void testCode() {
        Day25SeaCucumber.main(null);
        assertThat(Log.getPart1Int(), is(429));
    }

}
