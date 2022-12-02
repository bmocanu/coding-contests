package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day02PasswordPolicyTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day02PasswordPolicy.setForcedFilePath("/test-ed2020/day02.txt");
    }

    @Test
    public void testCode() {
        Day02PasswordPolicy.main(null);
        assertThat(Log.getPart1Int(), is(414));
        assertThat(Log.getPart2Int(), is(413));
    }

}
