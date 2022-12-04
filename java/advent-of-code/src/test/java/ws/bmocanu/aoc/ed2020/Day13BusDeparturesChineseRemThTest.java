package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day13BusDeparturesChineseRemThTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day13BusDeparturesChineseRemTh.setForcedFilePath("/test-ed2020/day13.txt");
    }

    @Test
    public void testCode() {
        Day13BusDeparturesChineseRemTh.main(null);
        assertThat(Log.getPart1Int(), is(2165));
        assertThat(Log.getPart2Long(), is(534035653563227L));
    }

}
