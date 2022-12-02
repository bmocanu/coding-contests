package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day16TicketValidNumbersAndFieldsTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day16TicketValidNumbersAndFields.setForcedFilePath("/test-ed2020/day16.txt");
    }

    @Test
    public void testCode() {
        Day16TicketValidNumbersAndFields.main(null);
        assertThat(Log.getPart1Int(), is(25916));
        assertThat(Log.getPart2Long(), is(2564529489989L));
    }

}
