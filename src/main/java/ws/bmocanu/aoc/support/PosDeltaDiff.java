package ws.bmocanu.aoc.support;

public class PosDeltaDiff {

    public static int getDeltaBetween(int v1, int v2) {
        if (v1 < v2) {
            return 1;
        } else if (v1 > v2) {
            return -1;
        }
        return 0;
    }

}
