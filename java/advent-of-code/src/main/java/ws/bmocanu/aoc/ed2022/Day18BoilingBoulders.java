package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Day18BoilingBoulders extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day18"));
        List<Cube> cubes = new SBind<>("(-?\\d+),(-?\\d+),(-?\\d+)", Cube.class, "x", "y", "z")
                .bindAll(input);
        cubes.forEach(cube -> cube.isFromInput = true);
        Log.part1(calculateExposedCubeFaces(cubes));

        final CubeSpace space = new CubeSpace();
        cubes.forEach(cube -> {
            space.minX = Math.min(space.minX, cube.x);
            space.minY = Math.min(space.minY, cube.y);
            space.minZ = Math.min(space.minZ, cube.z);
            space.maxX = Math.max(space.maxX, cube.x + 1);
            space.maxY = Math.max(space.maxY, cube.y + 1);
            space.maxZ = Math.max(space.maxZ, cube.z + 1);
        });

        // Part 2 phase 1: add air cubes that are completely surrounded by input cubes
        for (int x = space.minX; x <= space.maxX; x++) {
            for (int y = space.minY; y <= space.maxY; y++) {
                for (int z = space.minZ; z <= space.maxZ; z++) {
                    if (!thereIsACubeWithTheseCoordinates(x, y, z, true, cubes)) {
                        Cube newAirCube = new Cube(x, y, z);
                        if (hasInputCubesReachableFromAllSides(newAirCube, cubes, space)) {
                            cubes.add(newAirCube);
                        }
                    }
                }
            }
        }

        // Part 2 phase 2: remove all the air cubes that are not completely surrounded
        // and do this until there is no other air cube to remove
        boolean atLeastOneAirCubeRemoved;
        do {
            atLeastOneAirCubeRemoved = false;
            for (Iterator<Cube> iterator = cubes.iterator(); iterator.hasNext(); ) {
                Cube cube = iterator.next();
                if (!cube.isFromInput && !hasConnectingCubesOfAnyTypeOnAllSides(cube, cubes)) {
                    iterator.remove();
                    atLeastOneAirCubeRemoved = true;
                }
            }
        } while (atLeastOneAirCubeRemoved);

        Log.part2(calculateExposedCubeFaces(cubes));
    }

    // ----------------------------------------------------------------------------------------------------

    private static final int[][] DELTAS = {{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};

    private static boolean hasInputCubesReachableFromAllSides(Cube test, List<Cube> cubes, CubeSpace space) {
        boolean[] reachable = new boolean[6];

        for (int index = 0; index < 6; index++) {
            int cX = test.x;
            int cY = test.y;
            int cZ = test.z;
            do {
                cX += DELTAS[index][0];
                cY += DELTAS[index][1];
                cZ += DELTAS[index][2];
                if (thereIsACubeWithTheseCoordinates(cX, cY, cZ, true, cubes)) {
                    reachable[index] = true;
                    break;
                }
            } while (XNum.intBetween(cX, space.minX, space.maxX) &&
                    XNum.intBetween(cY, space.minY, space.maxY) &&
                    XNum.intBetween(cZ, space.minZ, space.maxZ));
            if (!reachable[index]) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasConnectingCubesOfAnyTypeOnAllSides(Cube test, List<Cube> cubes) {
        for (int index = 0; index < 6; index++) {
            int cX = test.x + DELTAS[index][0];
            int cY = test.y + DELTAS[index][1];
            int cZ = test.z + DELTAS[index][2];
            if (!thereIsACubeWithTheseCoordinates(cX, cY, cZ, false, cubes)) {
                return false;
            }
        }
        return true;
    }

    private static boolean thereIsACubeWithTheseCoordinates(int x, int y, int z,
                                                            boolean restrictToInput, List<Cube> cubes) {
        Optional<Cube> result = cubes.stream()
                .filter(cube -> (
                        (!restrictToInput || cube.isFromInput) && cube.x == x && cube.y == y && cube.z == z))
                .findFirst();
        return result.isPresent();
    }

    private static int calculateExposedCubeFaces(List<Cube> cubes) {
        cubes.forEach(cube -> {
            cube.exposedFaces = 6;
        });

        for (int i1 = 0; i1 < cubes.size() - 1; i1++) {
            Cube c1 = cubes.get(i1);
            for (int i2 = i1 + 1; i2 < cubes.size(); i2++) {
                Cube c2 = cubes.get(i2);
                if (Math.abs(c1.x - c2.x) +
                        Math.abs(c1.y - c2.y) +
                        Math.abs(c1.z - c2.z) == 1) {
                    c1.exposedFaces--;
                    c2.exposedFaces--;
                }
            }
        }
        return cubes.stream().mapToInt(cube -> cube.exposedFaces).sum();
    }

    // ----------------------------------------------------------------------------------------------------

    public static class Cube {
        int x;
        int y;
        int z;
        int exposedFaces;
        boolean isFromInput;

        @SuppressWarnings("unused")
        public Cube() {
        }

        public Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class CubeSpace {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
    }

}
