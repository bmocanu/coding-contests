package ws.bmocanu.aoc.ed2020;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day21AllergenAssessmentTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day21AllergenAssessment.setForcedFilePath("/test-ed2020/day21.txt");
    }

    @Test
    public void testCode() {
        Day21AllergenAssessment.main(null);
        assertThat(Log.getPart1Int(), is(2786));
        assertThat(Log.getPart2String(), is("prxmdlz,ncjv,knprxg,lxjtns,vzzz,clg,cxfz,qdfpq"));
    }

}
