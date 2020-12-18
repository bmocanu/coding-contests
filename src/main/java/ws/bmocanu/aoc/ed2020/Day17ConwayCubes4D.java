package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day17ConwayCubes4D extends SolutionBase {

    public static int OFF = 30;

    public static void main1(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day17"));
        int[][][] origMat = new int[OFF * 2][][];
        for (int i1 = 0; i1 < origMat.length; i1++) {
            origMat[i1] = new int[OFF * 2][];
            for (int i2 = 0; i2 < origMat.length; i2++) {
                origMat[i1][i2] = new int[OFF * 2];
            }
        }

        int width = stringLines.get(0).length();
        int height = stringLines.size();
        int start = (OFF - width) / 2;
        for (int row = 0; row < stringLines.size(); row++) {
            String line = stringLines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    origMat[OFF / 2][start + row][start + col] = 1;
                }
            }
        }

        int[][][] curMat = cloneMat(origMat);
        int[][][] newMat;
        for (int cycle = 0; cycle < 6; cycle++) {
            newMat = cloneMat(origMat);
            for (int x = 1; x < curMat.length - 1; x++) {
                for (int y = 1; y < curMat[x].length - 1; y++) {
                    for (int z = 1; z < curMat[x][y].length - 1; z++) {

                        int activeNeig = 0;
                        for (int dx = -1; dx <= 1; dx++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                for (int dz = -1; dz <= 1; dz++) {
                                    if (dx != 0 || dy != 0 || dz != 0) {
                                        if (curMat[x + dx][y + dy][z + dz] == 1) {
                                            activeNeig++;
                                        }
                                    }
                                }
                            }
                        }

                        if (curMat[x][y][z] == 1) {
                            if (activeNeig == 2 || activeNeig == 3) {
                                newMat[x][y][z] = 1;
                            } else {
                                newMat[x][y][z] = 0;
                            }
                        } else {
                            if (activeNeig == 3) {
                                newMat[x][y][z] = 1;
                            } else {
                                newMat[x][y][z] = 0;
                            }
                        }
                    }
                }
            }
            curMat = newMat;
        }

        int count = 0;
        for (int x = 0; x < curMat.length; x++) {
            for (int y = 0; y < curMat[x].length; y++) {
                for (int z = 0; z < curMat[x][y].length; z++) {
                    if (curMat[x][y][z] == 1) {
                        count++;
                    }
                }
            }
        }

        Log.part1(count);

        Log.part2(0);
    }

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList(filePath("day17"));
        int[][][][] origMat = new int[OFF * 2][][][];
        for (int i1 = 0; i1 < origMat.length; i1++) {
            origMat[i1] = new int[OFF * 2][][];
            for (int i2 = 0; i2 < origMat[i1].length; i2++) {
                origMat[i1][i2] = new int[OFF * 2][];
                for (int i3 = 0; i3 < origMat[i1][i2].length; i3++) {
                    origMat[i1][i2][i3] = new int[OFF * 2];
                }
            }
        }

        int width = stringLines.get(0).length();
        int start = (OFF - width) / 2;
        for (int row = 0; row < stringLines.size(); row++) {
            String line = stringLines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    origMat[OFF / 2][OFF / 2][start + row][start + col] = 1;
                }
            }
        }

        int[][][][] curMat = cloneMat2(origMat);
        int[][][][] newMat;
        for (int cycle = 0; cycle < 6; cycle++) {
            newMat = cloneMat2(curMat);
            for (int x = 1; x < curMat.length - 1; x++) {
                for (int y = 1; y < curMat[x].length - 1; y++) {
                    for (int z = 1; z < curMat[x][y].length - 1; z++) {
                        for (int w = 1; w < curMat[x][y][z].length - 1; w++) {

                            int activeNeig = 0;
                            for (int dx = -1; dx <= 1; dx++) {
                                for (int dy = -1; dy <= 1; dy++) {
                                    for (int dz = -1; dz <= 1; dz++) {
                                        for (int dw = -1; dw <= 1; dw++) {
                                            if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                                                if (curMat[x + dx][y + dy][z + dz][w + dw] == 1) {
                                                    activeNeig++;
                                                }
                                            }
                                        }
                                    }
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

        int count = 0;
        for (int x = 0; x < curMat.length; x++) {
            for (int y = 0; y < curMat[x].length; y++) {
                for (int z = 0; z < curMat[x][y].length; z++) {
                    for (int w = 0; w < curMat[x][y][z].length; w++) {
                        if (curMat[x][y][z][w] == 1) {
                            count++;
                        }
                    }
                }
            }
        }

        Log.part1(count);

        Log.part2(0);
    }

    public static int[][][] cloneMat(int[][][] mat) {
        int[][][] newMat = new int[mat.length][][];
        for (int x = 0; x < mat.length; x++) {
            newMat[x] = new int[mat[x].length][];
            for (int y = 0; y < mat[x].length; y++) {
                newMat[x][y] = new int[mat[x][y].length];
                for (int z = 0; z < mat[x][y].length; z++) {
                    newMat[x][y][z] = mat[x][y][z];
                }
            }
        }
        return newMat;
    }


    public static int[][][][] cloneMat2(int[][][][] mat) {
        int[][][][] newMat = new int[mat.length][][][];
        for (int x = 0; x < mat.length; x++) {
            newMat[x] = new int[mat[x].length][][];
            for (int y = 0; y < mat[x].length; y++) {
                newMat[x][y] = new int[mat[x][y].length][];
                for (int z = 0; z < mat[x][y].length; z++) {
                    newMat[x][y][z] = new int[mat[x][y][z].length];
                    for (int w = 0; w < mat[x][y][z].length; w++) {
                        newMat[x][y][z][w] = mat[x][y][z][w];
                    }
                }
            }
        }
        return newMat;
    }

}
