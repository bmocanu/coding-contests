package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day16PacketDecoderTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day16PacketDecoder.setForcedFilePath("/test-ed2021/day16.txt");
    }

    @Test
    public void testCode() {
        Day16PacketDecoder.main(null);
        assertThat(Log.getPart1Int(), is(969));
        assertThat(Log.getPart2Long(), is(124921618408L));
    }

}
