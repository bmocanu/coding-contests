package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.PosDelta3D;
import ws.bmocanu.aoc.support.PosDelta4D;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day17ConwayCubes4D extends SolutionBase {

    public static int OFF = 20;
    public static int CENTER = OFF / 2;

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day17"));
        int[][][][] origMat = Utils.createIntMatrix4(OFF * 2, OFF * 2, OFF * 2, OFF * 2);

        int width = stringLines.get(0).length();
        int start = (OFF - width) / 2;
        for (int row = 0; row < stringLines.size(); row++) {
            String line = stringLines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    origMat[CENTER][CENTER][start + row][start + col] = 1;
                }
            }
        }

        int[][][][] curMat;
        int[][][][] newMat;

        // Part 1
        curMat = Utils.cloneIntMatrix4(origMat);
        for (int cycle = 0; cycle < 6; cycle++) {
            newMat = Utils.cloneIntMatrix4(curMat);
            for (int x = 1; x < curMat.length - 1; x++) {
                for (int y = 1; y < curMat[x].length - 1; y++) {
                    for (int z = 1; z < curMat[x][y].length - 1; z++) {
                        int activeNeig = 0;
                        for (PosDelta3D delta : PosDelta3D.values) {
                            if (curMat[OFF / 2][x + delta.deltaX][y + delta.deltaY][z + delta.deltaZ] == 1) {
                                activeNeig++;
                            }
                        }
                        if (curMat[CENTER][x][y][z] == 1) {
                            if (activeNeig == 2 || activeNeig == 3) {
                                newMat[CENTER][x][y][z] = 1;
                            } else {
                                newMat[CENTER][x][y][z] = 0;
                            }
                        } else {
                            if (activeNeig == 3) {
                                newMat[CENTER][x][y][z] = 1;
                            } else {
                                newMat[CENTER][x][y][z] = 0;
                            }
                        }
                    }
                }
            }
            curMat = newMat;
        }

        // 401
        // 2224
        Log.part1(Utils.iterateIntMatrix4ToSum(curMat, (x, y, z, w, value) -> value));

        // Part 2
        curMat = Utils.cloneIntMatrix4(origMat);
        for (int cycle = 0; cycle < 6; cycle++) {
            newMat = Utils.cloneIntMatrix4(curMat);
            for (int x = 1; x < curMat.length - 1; x++) {
                for (int y = 1; y < curMat[x].length - 1; y++) {
                    for (int z = 1; z < curMat[x][y].length - 1; z++) {
                        for (int w = 1; w < curMat[x][y][z].length - 1; w++) {
                            int activeNeig = 0;
                            for (PosDelta4D delta : PosDelta4D.values) {
                                if (curMat[x + delta.deltaX][y + delta.deltaY][z + delta.deltaZ][w + delta.deltaW] == 1) {
                                    activeNeig++;
                                }
                            }
                            if (curMat[x][y][z][w] == 1) {
                                if (activeNeig == 2 || activeNeig == 3) {
                                    newMat[x][y][z][w] = 1;
                                } else {
                                    newMat[x][y][z][w] = 0;
                                }
                            } else {
                                if (activeNeig == 3) {
                                    newMat[x][y][z][w] = 1;
                                } else {
                                    newMat[x][y][z][w] = 0;
                                }
                            }
                        }
                    }
                }
            }
            curMat = newMat;
        }

        Log.part2(Utils.iterateIntMatrix4ToSum(curMat, (x, y, z, w, value) -> value));
    }

}