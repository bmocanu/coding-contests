package test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestParamTest {

    @ParameterizedTest
    @MethodSource("generateTestInput")
    public void testSomething(int arg0, int arg1) throws IOException {
        System.out.println("Doing a bit of logging");
        System.out.println("Running test with arg0=" + arg0 + ", arg1=" + arg1);
        System.out.println("And some more logging here");
        if (arg0 > 2) {
            throw new IOException("This failed massively");
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private static List<Arguments> generateTestInput() {
        List<Arguments> result = new ArrayList<>();
        result.add(Arguments.of(1, 2));
        result.add(Arguments.of(2, 3));
        result.add(Arguments.of(3, 4));
        result.add(Arguments.of(4, 5));
        result.add(Arguments.of(6, 7));
        return result;
    }

}
