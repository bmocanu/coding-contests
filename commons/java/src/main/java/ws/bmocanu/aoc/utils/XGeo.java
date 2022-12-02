package ws.bmocanu.aoc.utils;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Point3D;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class XGeo {

    public static boolean pointsOnTheSameLine(Point p1, Point p2, Point p3) {
        double p12Distance = distance(p1, p2);
        double p23Distance = distance(p2, p3);
        double p13Distance = distance(p1, p3);
        return Math.abs(p13Distance - (p12Distance + p23Distance)) < 0.00001d;
    }

    public static double distance(Point p1, Point p2) {
        int xPart = p1.x - p2.x;
        int yPart = p1.y - p2.y;
        return Math.sqrt(xPart * xPart + yPart * yPart);
    }

    public static void rotatePointAroundCenter(Point point, Point center, int radius, double angleInDegrees) {
        double angleInRadians = angleInDegrees * Math.PI / 180;
        double sin = Math.sin(angleInRadians);
        double cos = Math.cos(angleInRadians);
        double startX = 0d;
        double startY = -radius;
        point.x = (int) (Math.round(startX * cos - startY * sin)) + center.x;
        point.y = (int) (Math.round(startX * sin + startY * cos)) + center.y;
    }

    public static double angleBetween3PointsInDegrees(Point p1, Point p2, Point p3) {
        double p1X = p1.x;
        double p1Y = p1.y;
        double p2X = p2.x;
        double p2Y = p2.y;
        double p3X = p3.x;
        double p3Y = p3.y;
        var result = Math.atan2(p3Y - p2Y, p3X - p2X) - Math.atan2(p1Y - p2Y, p1X - p2X);
        result = result * 180 / Math.PI;
        if (result < 0) {
            result = 360 + result;
        }
        return result;
    }

    /**
     * Compute all translation and rotations of a 3D point. Dir24 goes from 0 to 23.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public static Point3D translatePoint3DByDir24(Point3D p, int dir24) {
        if (dir24 == 0) return new Point3D(p.x, p.y, p.z);
        if (dir24 == 1) return new Point3D(p.y, -p.x, p.z);
        if (dir24 == 2) return new Point3D(-p.x, -p.y, p.z);
        if (dir24 == 3) return new Point3D(-p.y, p.x, p.z);
        if (dir24 == 4) return new Point3D(p.y, p.z, p.x);
        if (dir24 == 5) return new Point3D(-p.x, p.z, p.y);
        if (dir24 == 6) return new Point3D(-p.y, p.z, -p.x);
        if (dir24 == 7) return new Point3D(p.x, p.z, -p.y);
        if (dir24 == 8) return new Point3D(p.z, p.x, p.y);
        if (dir24 == 9) return new Point3D(p.z, p.y, -p.x);
        if (dir24 == 10) return new Point3D(p.z, -p.x, -p.y);
        if (dir24 == 11) return new Point3D(p.z, -p.y, p.x);
        if (dir24 == 12) return new Point3D(p.y, p.x, -p.z);
        if (dir24 == 13) return new Point3D(-p.x, p.y, -p.z);
        if (dir24 == 14) return new Point3D(-p.y, -p.x, -p.z);
        if (dir24 == 15) return new Point3D(p.x, -p.y, -p.z);
        if (dir24 == 16) return new Point3D(p.x, -p.z, p.y);
        if (dir24 == 17) return new Point3D(p.y, -p.z, -p.x);
        if (dir24 == 18) return new Point3D(-p.x, -p.z, -p.y);
        if (dir24 == 19) return new Point3D(-p.y, -p.z, p.x);
        if (dir24 == 20) return new Point3D(-p.z, p.y, p.x);
        if (dir24 == 21) return new Point3D(-p.z, -p.x, p.y);
        if (dir24 == 22) return new Point3D(-p.z, -p.y, -p.x);
        // if (dir24 == 23)
        return new Point3D(-p.z, p.x, -p.y);
    }

}
