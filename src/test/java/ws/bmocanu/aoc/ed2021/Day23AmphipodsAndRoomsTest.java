package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day23AmphipodsAndRoomsTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day23AmphipodsAndRooms.setForcedFilePath("/test-ed2021/day23.txt");
    }

    @Test
    public void testCode() {
        Day23AmphipodsAndRooms.main(null);
        assertThat(Log.getPart1Int(), is(0)); // this part was done by hand
        assertThat(Log.getPart2Int(), is(55136));
    }

}
