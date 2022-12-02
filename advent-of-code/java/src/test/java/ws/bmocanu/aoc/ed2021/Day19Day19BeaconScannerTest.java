package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day19Day19BeaconScannerTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day19BeaconScanner.setForcedFilePath("/test-ed2021/day19.txt");
    }

    @Test
    public void testCode() {
        Day19BeaconScanner.main(null);
        assertThat(Log.getPart1Int(), is(414));
        assertThat(Log.getPart2Int(), is(13000));
    }

}
