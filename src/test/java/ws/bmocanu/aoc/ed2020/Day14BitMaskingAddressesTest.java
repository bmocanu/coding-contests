package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day14BitMaskingAddressesTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day14BitMaskingAddresses.setForcedFilePath("/test-ed2020/day14.txt");
    }

    @Test
    public void testCode() {
        Day14BitMaskingAddresses.main(null);
        assertThat(Log.getPart1BigInt().toString(), is("8570568288597"));
        assertThat(Log.getPart2BigInt().toString(), is("3289441921203"));
    }

}
