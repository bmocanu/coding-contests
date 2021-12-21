package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day21DiracDiceTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        // no input for this day, the values are right in the code
        // Day21DiracDice.setForcedFilePath("/test-ed2021/day21.txt");
    }

    @Test
    public void testCode() {
        Day21DiracDice.main(null);
        assertThat(Log.getPart1Int(), is(908091));
        assertThat(Log.getPart2Long(), is(190897246590017L));
    }

}
