package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day01RecordsSumTo2020Test {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day01RecordsSumTo2020.setForcedFilePath("/test-ed2020/day01.txt");
    }

    @Test
    public void testCode() {
        Day01RecordsSumTo2020.main(null);
        assertThat(Log.getPart1Int(), is(793524));
        assertThat(Log.getPart2Int(), is(61515678));
    }

}
