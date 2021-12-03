package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day03BinaryDiagnosticReportTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day03BinaryDiagnosticReport.setForcedFilePath("/test-ed2021/day03.txt");
    }

    @Test
    public void testCode() {
        Day03BinaryDiagnosticReport.main(null);
        assertThat(Log.getPart1Int(), is(2583164));
        assertThat(Log.getPart2Int(), is(2784375));
    }

}
