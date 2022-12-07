package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day07NoSpaceLeftOnDeviceTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day07NoSpaceLeftOnDevice.setForcedFilePath("/test-ed2022/day07.txt");
    }

    @Test
    public void testCode() {
        Day07NoSpaceLeftOnDevice.main(null);
        assertThat(Log.getPart1Int(), is(1086293));
        assertThat(Log.getPart2Int(), is(366028));
    }

}
