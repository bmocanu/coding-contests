package ws.bmocanu.aoc.support;

import java.util.ArrayList;
import java.util.List;

public class PosDelta3D {

    public static List<PosDelta3D> values;

    static {
        values = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        values.add(new PosDelta3D(x, y, z));
                    }
                }
            }
        }
    }

    public int deltaX;

    public int deltaY;

    public int deltaZ;

    public PosDelta3D(int deltaX, int deltaY, int deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }

}
