package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.Point3D;
import ws.bmocanu.aoc.utils.*;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day19BeaconScanner extends SolutionBase {

    public static void main(String[] args) {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day19"));
        List<Scanner> scanners = new ArrayList<>();
        Scanner currentRead = null;
        SBind<Point3D> binder = new SBind<>("([-0-9]+),([-0-9]+),([-0-9]+)", Point3D.class, "x", "y", "z");
        for (String line : inputLines) {
            if (line.contains("---")) {
                currentRead = new Scanner();
                scanners.add(currentRead);
            } else if (!line.isEmpty()) {
                currentRead.beacons.add(binder.bindOne(line));
            }
        }

        Scanner sc0 = scanners.get(0);
        sc0.center = new Point3D(0, 0, 0);
        Set<Point3D> beaconsFound = new HashSet<>(sc0.beacons);

        SList<Scanner> scannersAligned = new SList<>(Arrays.asList(sc0));
        SList<Scanner> scannersToProcess = new SList<>(scanners);
        scannersToProcess.remove(sc0);

        while (!scannersToProcess.isEmpty()) {
            Scanner s2 = scannersToProcess.first();
            boolean scannerMatched = false;
            for (Scanner s1 : scannersAligned) {
                scannerMatched = scannerMatched || matchBeacons(s1, s2, beaconsFound);
                if (scannerMatched) {
                    scannersAligned.add(s2);
                    scannersToProcess.removeFirst();
                    break;
                }
            }
            if (!scannerMatched) {
                scannersToProcess.rollToTheLeft();
            }
        }
        Log.part1(beaconsFound.size());

        int maxDistance = Integer.MIN_VALUE;
        for (Scanner s1 : scanners) {
            for (Scanner s2 : scanners) {
                if (s1 != s2) {
                    int currentDistance = s1.center.taxicabDistanceTo(s2.center);
                    if (currentDistance > maxDistance) {
                        maxDistance = currentDistance;
                    }
                }
            }
        }
        Log.part2(maxDistance);
    }

    public static boolean matchBeacons(Scanner sc1, Scanner sc2, Set<Point3D> beaconsFound) {
        for (int direction = 0; direction < 24; direction++) {
            SMap<Point3D, Integer> deltas = new SMap<>();
            for (Point3D p1 : sc1.beacons) {
                for (Point3D p2 : sc2.beacons) {
                    Point3D transP2 = XGeo.translatePoint3DByDir24(p2, direction);
                    Point3D currentDelta = new Point3D(
                            p1.x - transP2.x,
                            p1.y - transP2.y,
                            p1.z - transP2.z);
                    Integer deltaMatches = deltas.getSafely(currentDelta, 0);
                    deltas.put(currentDelta, deltaMatches + 1);
                }
            }
            Optional<Map.Entry<Point3D, Integer>> match =
                    deltas.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() >= 12)
                            .findFirst();
            if (match.isPresent()) {
                Point3D s2Delta = match.get().getKey();
                List<Point3D> newSC2Beacons = new ArrayList<>();
                for (Point3D s2Beacon : sc2.beacons) {
                    Point3D translatedS2Beacon = XGeo.translatePoint3DByDir24(s2Beacon, direction);
                    newSC2Beacons.add(new Point3D(
                            translatedS2Beacon.x + s2Delta.x,
                            translatedS2Beacon.y + s2Delta.y,
                            translatedS2Beacon.z + s2Delta.z));
                }
                sc2.beacons = newSC2Beacons;
                sc2.center = s2Delta;
                beaconsFound.addAll(newSC2Beacons);
                return true;
            }
        }
        return false;
    }

    static class Scanner {
        List<Point3D> beacons = new ArrayList<>();
        Point3D center;
    }

}
