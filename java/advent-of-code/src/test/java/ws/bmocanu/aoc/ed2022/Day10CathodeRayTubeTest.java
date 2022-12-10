package ws.bmocanu.aoc.ed2022;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ws.bmocanu.aoc.support.Log;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day10CathodeRayTubeTest {

    @BeforeEach
    public void setUp() {
        Log.reset();
        Day10CathodeRayTube.setForcedFilePath("/test-ed2022/day10.txt");
    }

    @Test
    public void testCode() {
        Day10CathodeRayTube.main(null);
        assertThat(Log.getPart1Int(), is(15220));
        assertThat(Log.getPart2String(), is("\n" +
                "###..####.####.####.#..#.###..####..##..\n" +
                "#..#.#.......#.#....#.#..#..#.#....#..#.\n" +
                "#..#.###....#..###..##...###..###..#..#.\n" +
                "###..#.....#...#....#.#..#..#.#....####.\n" +
                "#.#..#....#....#....#.#..#..#.#....#..#.\n" +
                "#..#.#....####.####.#..#.###..#....#..#.\n"));
    }

}
