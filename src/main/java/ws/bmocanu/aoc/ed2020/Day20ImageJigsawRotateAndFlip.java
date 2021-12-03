package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day20ImageJigsawRotateAndFlip extends SolutionBase {

    // P1: 20913499394191
    // P2: 2209

    static List<Tile> tiles = new ArrayList<>();
    static boolean[] tileTaken;
    static int size = 12;
    static Tile[][] matrix;

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day20"));
        int[][] pixels = Utils.createIntMatrix2(10, 10);
        Tile currentTile = null;
        int pixelsLine = 0;
        for (String line : stringLines) {
            if (line.startsWith("Tile")) {
                if (currentTile != null) {
                    CharMat currentMat = new CharMat(10, pixels);
                    currentTile.charMatList = getAllVariantsForCharMat(currentMat);
                    tiles.add(currentTile);
                }
                currentTile = new Tile();
                pixelsLine = 0;
                currentTile.id = Long.parseLong(line.substring(line.indexOf(' ') + 1, line.length() - 1));
            } else if (!line.isBlank()) {
                for (int x = 0; x < line.length(); x++) {
                    pixels[x][pixelsLine] = (line.charAt(x) == '#' ? 1 : 0);
                }
                pixelsLine++;
            }
        }
        tileTaken = new boolean[tiles.size()];
        matrix = new Tile[size][];
        for (int index = 0; index < matrix.length; index++) {
            matrix[index] = new Tile[size];
        }

        calculatePart1(0, 0, 1);

        pixels = Utils.createIntMatrix2(8 * size, 8 * size);
        for (int y = 0; y < size; y++) {
            for (int w = 1; w < 9; w++) {
                for (int x = 0; x < size; x++) {
                    for (int v = 1; v < 9; v++) {
                        pixels[v - 1 + x * 8][w - 1 + y * 8] = matrix[x][y].variant.pixels[v][w];
                    }
                }
            }
        }
        calculatePart2(new CharMat(8 * size, pixels));
    }

    // ----------------------------------------------------------------------------------------------------

    public static boolean calculatePart1(int x, int y, int depth) {
        if (y >= size) {
            Log.part1(matrix[0][0].id * matrix[size - 1][0].id *
                    matrix[0][size - 1].id * matrix[size - 1][size - 1].id);
            return true;
        }
        for (int index = 0; index < tiles.size(); index++) {
            if (!tileTaken[index]) {
                Tile tile = tiles.get(index);
                for (CharMat variantMat : tile.charMatList) {
                    boolean fitsIn = true;
                    if (x > 0) {
                        fitsIn = fitsIn && (variantMat.left == matrix[x - 1][y].variant.right);
                    }
                    if (y > 0) {
                        fitsIn = fitsIn && (variantMat.top == matrix[x][y - 1].variant.bottom);
                    }
                    if (fitsIn) {
                        tileTaken[index] = true;
                        matrix[x][y] = tile;
                        matrix[x][y].variant = variantMat;
                        if (calculatePart1((x < size - 1 ? x + 1 : 0), (x < size - 1 ? y : y + 1), depth + 1)) {
                            return true;
                        }
                        tileTaken[index] = false;
                    }
                }
            }
        }
        return false;
    }

    // ----------------------------------------------------------------------------------------------------

    // #.###...#.##...#.##O###.
    // .O##.#OO.###OO##..OOO##.
    // ..O#.O..O..O.#O##O##.###

    private static final int[][] monsterPattern = new int[][]{
            new int[]{19, 0},
            new int[]{1, 1},
            new int[]{6, 1},
            new int[]{7, 1},
            new int[]{12, 1},
            new int[]{13, 1},
            new int[]{18, 1},
            new int[]{19, 1},
            new int[]{20, 1},
            new int[]{2, 2},
            new int[]{5, 2},
            new int[]{8, 2},
            new int[]{11, 2},
            new int[]{14, 2},
            new int[]{17, 2}
    };

    public static void calculatePart2(CharMat startMat) {
        int p2Size = 96;
        List<CharMat> allMats = getAllVariantsForCharMat(startMat);
        for (CharMat mat : allMats) {
            int monstersFound = 0;
            for (int x = 0; x < p2Size - 19; x++) {
                for (int y = 0; y < p2Size - 2; y++) {
                    boolean allMatched = true;
                    for (int[] monsterCoords : monsterPattern) {
                        allMatched = allMatched && mat.pixels[x + monsterCoords[0]][y + monsterCoords[1]] == 1;
                    }
                    if (allMatched) {
                        monstersFound++;
                        for (int coordIndex = 0; coordIndex < monsterPattern.length; coordIndex++) {
                            mat.pixels[x + monsterPattern[coordIndex][0]][y + monsterPattern[coordIndex][1]] = 0;
                        }
                    }
                }
            }
            if (monstersFound > 0) {
                Log.part2(Utils.iterateIntMatrix2ToSumOfValues(mat.pixels));
                return;
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------

    private static List<CharMat> getAllVariantsForCharMat(CharMat startMat) {
        List<CharMat> allVariants = new ArrayList<>();
        CharMat currentMatRL = startMat.rotateLeft();
        CharMat currentMatRLL = currentMatRL.rotateLeft();
        CharMat currentMatRLLL = currentMatRLL.rotateLeft();
        allVariants.add(startMat);
        allVariants.add(currentMatRL);
        allVariants.add(currentMatRLL);
        allVariants.add(currentMatRLLL);
        allVariants.add(startMat.flipHorizontally());
        allVariants.add(startMat.flipVertically());
        allVariants.add(currentMatRL.flipHorizontally());
        allVariants.add(currentMatRL.flipVertically());
        return allVariants;
    }

    // ----------------------------------------------------------------------------------------------------

    public static class Tile {
        long id;
        List<CharMat> charMatList = new ArrayList<>();
        CharMat variant;
    }

    public static class CharMat {
        int size;
        int[][] pixels;
        long top;
        long left;
        long right;
        long bottom;

        public CharMat(int size) {
            this.size = size;
            this.pixels = Utils.createIntMatrix2(size, size);
        }

        public CharMat(int size, int[][] pixels) {
            this.size = size;
            this.pixels = Utils.cloneIntMatrix2(pixels);
            initSideNums();
        }

        public CharMat rotateLeft() {
            CharMat newMat = new CharMat(size);
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    newMat.pixels[y][size - x - 1] = pixels[x][y];
                }
            }
            newMat.initSideNums();
            return newMat;
        }

        public CharMat flipHorizontally() {
            CharMat newMat = new CharMat(size);
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    newMat.pixels[size - x - 1][y] = pixels[x][y];
                }
            }
            newMat.initSideNums();
            return newMat;
        }

        public CharMat flipVertically() {
            CharMat newMat = new CharMat(size);
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    newMat.pixels[x][size - y - 1] = pixels[x][y];
                }
            }
            newMat.initSideNums();
            return newMat;
        }

        public void initSideNums() {
            long mult = 1;
            int index;
            for (index = 0; index < size; index++) {
                top += mult * pixels[size - index - 1][0];
                right += mult * pixels[size - 1][size - index - 1];
                bottom += mult * pixels[size - index - 1][size - 1];
                left += mult * pixels[0][size - index - 1];
                mult = mult * 2;
            }
        }
    }

}
