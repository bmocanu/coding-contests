package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.ed2020.Day01RecordsSumTo2020;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day01MeasuringOceanFloorTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day01MeasuringOceanFloor.setForcedFilePath("/test-ed2021/day01.txt");
    }

    @Test
    public void testCode() {
        Day01MeasuringOceanFloor.main(null);
        assertThat(Log.getPart1Int(), is(1581));
        assertThat(Log.getPart2Int(), is(1618));
    }
    
}
