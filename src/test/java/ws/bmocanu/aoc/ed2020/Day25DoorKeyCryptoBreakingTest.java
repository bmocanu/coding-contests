package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day25DoorKeyCryptoBreakingTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day25DoorKeyCryptoBreaking.setForcedFilePath("/test-ed2020/day25.txt");
    }

    @Test
    public void testCode() {
        Day25DoorKeyCryptoBreaking.main(null);
        assertThat(Log.getPart1Long(), is(11707042L));
    }

}
