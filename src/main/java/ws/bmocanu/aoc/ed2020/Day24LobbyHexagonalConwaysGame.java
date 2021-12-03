package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day24LobbyHexagonalConwaysGame extends SolutionBase {

    static int floorSize = 500;
    static int startX = floorSize / 2;
    static int startY = floorSize / 2;
    static int[][] floor;
    static String[] commands = new String[]{"se", "sw", "ne", "nw", "e", "w"};

    public static void main(String[] args) {
        List<String> stringLines = XRead.fileAsStringPerLineToStringList(filePath("day24"));
        floor = Utils.createIntMatrix2(floorSize, floorSize);
        for (String line : stringLines) {
            Point cursor = Point.from(startX, startY);
            int charIndex = 0;
            while (charIndex < line.length()) {
                String dir = String.valueOf(line.charAt(charIndex));
                if (dir.equals("s") || dir.equals("n")) {
                    charIndex++;
                    dir += String.valueOf(line.charAt(charIndex));
                }
                switch (dir) {
                    case "se":
                        cursor.x++;
                        cursor.y++;
                        break;
                    case "sw":
                        cursor.x--;
                        cursor.y++;
                        break;
                    case "nw":
                        cursor.x--;
                        cursor.y--;
                        break;
                    case "ne":
                        cursor.x++;
                        cursor.y--;
                        break;
                    case "e":
                        cursor.x += 2;
                        break;
                    case "w":
                        cursor.x -= 2;
                        break;
                }
                charIndex++;
            }
            floor[cursor.x][cursor.y] = 1 - floor[cursor.x][cursor.y];
        }
        Log.part1(Utils.iterateIntMatrix2ToSumOfValues(floor));

        for (int round = 0; round < 100; round++) {
            int[][] curMat = Utils.cloneIntMatrix2(floor);
            for (int y = 1; y < floorSize - 1; y++) {
                for (int x = 2; x < floorSize - 2; x++) {
                    int blackTiles = 0;
                    for (String command : commands) {
                        int cx = x;
                        int cy = y;
                        switch (command) {
                            case "se":
                                cx++;
                                cy++;
                                break;
                            case "sw":
                                cx--;
                                cy++;
                                break;
                            case "nw":
                                cx--;
                                cy--;
                                break;
                            case "ne":
                                cx++;
                                cy--;
                                break;
                            case "e":
                                cx += 2;
                                break;
                            case "w":
                                cx -= 2;
                                break;
                        }
                        if (floor[cx][cy] == 1) {
                            blackTiles++;
                        }
                    }
                    if (floor[x][y] == 1 && (blackTiles == 0 || blackTiles > 2)) {
                        curMat[x][y] = 0;
                    }
                    if (floor[x][y] == 0 && (blackTiles == 2)) {
                        curMat[x][y] = 1;
                    }
                }
            }
            floor = curMat;
        }

        Log.part2(Utils.iterateIntMatrix2ToSumOfValues(floor));

        // 360
        // 3924
    }

}
