package ws.bmocanu.aoc.utils;

import static java.util.Arrays.stream;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class MathUtils {

    /*
     * Chinese remainder theorem code, taken from here:
     * https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    public static long calculateChineseRemainder(int[] n, int[] a) {
        long prod = stream(n).reduce(1, (i, j) -> i * j);
        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * chineseRemainderMulInv(p, n[i]) * p;
        }
        return sm % prod;
    }

    // ----------------------------------------------------------------------------------------------------

    /*
     * Chinese remainder theorem code, taken from here:
     * https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    private static long chineseRemainderMulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1)
            return 1;

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }

}
