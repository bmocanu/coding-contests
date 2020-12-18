package ws.bmocanu.aoc.support;

import java.util.ArrayList;
import java.util.List;

public class PosDelta4D {

    public static List<PosDelta4D> values;

    static {
        values = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    for (int w = -1; w <= 1; w++) {
                        if (x != 0 || y != 0 || z != 0 || w != 0) {
                            values.add(new PosDelta4D(x, y, z, w));
                        }
                    }
                }
            }
        }
    }

    public int deltaX;

    public int deltaY;

    public int deltaZ;

    public int deltaW;

    public PosDelta4D(int deltaX, int deltaY, int deltaZ, int deltaW) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.deltaW = deltaW;
    }

}
