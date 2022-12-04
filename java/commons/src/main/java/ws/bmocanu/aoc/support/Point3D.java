package ws.bmocanu.aoc.support;

import java.util.Objects;

@SuppressWarnings("unused")
public class Point3D {
    public int x;
    public int y;
    public int z;

    public Point3D() {
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x == point3D.x && y == point3D.y && z == point3D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public int taxicabDistanceTo(Point3D point2) {
        return taxicabDistanceTo(point2.x, point2.y, point2.z);
    }

    public int taxicabDistanceTo(int otherX, int otherY, int otherZ) {
        return Math.abs(x - otherX) + Math.abs(y - otherY) + Math.abs(z - otherZ);
    }

}
