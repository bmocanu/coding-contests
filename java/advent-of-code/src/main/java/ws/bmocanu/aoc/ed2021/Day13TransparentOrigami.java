package ws.bmocanu.aoc.ed2021;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XMatrix;
import ws.bmocanu.aoc.utils.XNum;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day13TransparentOrigami extends SolutionBase {

    public static class FoldInstruction {
        public String axis;
        public int coord;
    }

    static int[][] matrix;
    static int width;
    static int height;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day13"));
        matrix = XMatrix.createIntMatrix2(2000, 2000);
        width = 0;
        height = 0;
        List<FoldInstruction> foldings = new ArrayList<>();
        SBind<FoldInstruction> bind = new SBind("fold along ([xy])=(\\d+)", FoldInstruction.class, "axis", "coord");
        for (String line : input) {
            if (line.contains(",")) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                matrix[x][y] = 1;
                width = XNum.max(width, x + 1);
                height = XNum.max(height, y + 1);
            } else if (line.contains("fold along")) {
                foldings.add(bind.bindOne(line));
            }
        }

        fold(foldings.get(0));
        Log.part1(XMatrix.countInIntMatrix2WhereValueIs(1, matrix, width, height));

        for (int index = 1; index < foldings.size(); index++) {
            fold(foldings.get(index));
        }
        Log.part2("\n" + XMatrix.printIntMatrix2WithValueMappingToChar(matrix, width, height, 0, ' ', 1, '#'));
    }

    public static void fold(FoldInstruction fold) {
        if (fold.axis.equals("x")) {
            for (int y = 0; y < height; y++) {
                for (int x = fold.coord + 1; x <= fold.coord * 2; x++) {
                    if (matrix[x][y] == 1) {
                        matrix[fold.coord * 2 - x][y] = 1;
                    }
                }
            }
            width = fold.coord;
        } else {
            for (int y = fold.coord + 1; y <= fold.coord * 2; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix[x][y] == 1) {
                        matrix[x][fold.coord * 2 - y] = 1;
                    }
                }
            }
            height = fold.coord;
        }
    }

}
