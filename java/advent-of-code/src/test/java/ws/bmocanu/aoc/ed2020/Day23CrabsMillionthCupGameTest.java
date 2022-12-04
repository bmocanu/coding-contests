package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day23CrabsMillionthCupGameTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        // game input: 643719258
    }

    @Test
    public void testCode() {
        Day23CrabsMillionthCupGame.main(null);
        assertThat(Log.getPart1String(), is("54896723"));
        assertThat(Log.getPart2Long(), is(146304752384L));
    }

}
