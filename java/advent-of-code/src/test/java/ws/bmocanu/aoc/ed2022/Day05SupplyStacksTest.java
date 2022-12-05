package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day05SupplyStacksTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day05SupplyStacks.setForcedFilePath("/test-ed2022/day05.txt");
    }

    @Test
    public void testCode() {
        Day05SupplyStacks.main(null);
        assertThat(Log.getPart1String(), is("ZRLJGSCTR"));
        assertThat(Log.getPart2String(), is("PRTTGRFPB"));
    }

}
