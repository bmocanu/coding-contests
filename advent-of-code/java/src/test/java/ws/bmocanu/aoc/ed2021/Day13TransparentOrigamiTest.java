package ws.bmocanu.aoc.ed2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day13TransparentOrigamiTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day13TransparentOrigami.setForcedFilePath("/test-ed2021/day13.txt");
    }

    @Test
    public void testCode() {
        Day13TransparentOrigami.main(null);
        assertThat(Log.getPart1Int(), is(687));
        assertThat(Log.getPart2String(), is("\n"
                                            + "####  ##  #  #  ##  #  # ###  ####  ##  \n"
                                            + "#    #  # # #  #  # # #  #  #    # #  # \n"
                                            + "###  #    ##   #    ##   ###    #  #    \n"
                                            + "#    # ## # #  #    # #  #  #  #   # ## \n"
                                            + "#    #  # # #  #  # # #  #  # #    #  # \n"
                                            + "#     ### #  #  ##  #  # ###  ####  ### \n"));
    }

}
