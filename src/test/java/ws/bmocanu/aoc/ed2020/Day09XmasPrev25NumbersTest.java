package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day09XmasPrev25NumbersTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
    }

    @Test
    public void testCode() {
        Day09XmasPrev25Numbers.main(null);
        assertThat(Log.getPart1Long(), is(70639851L));
        assertThat(Log.getPart2Long(), is(8249240L));
    }

}
