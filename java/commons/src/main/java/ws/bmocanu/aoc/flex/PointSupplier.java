package ws.bmocanu.aoc.flex;

public interface PointSupplier {

    Point pointOrNull(int x, int y);

    Point point(int x, int y);

    int width();

    int height();

}
