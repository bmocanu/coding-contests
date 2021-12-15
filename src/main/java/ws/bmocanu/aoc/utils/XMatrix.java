package ws.bmocanu.aoc.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class XMatrix {

    public static boolean[][] createBooleanMatrix2(int dim1, int dim2) {
        boolean[][] matrix = new boolean[dim1][];
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = new boolean[dim2];
        }
        return matrix;
    }

    public static int[][] createIntMatrix2(int dim1, int dim2) {
        int[][] matrix = new int[dim1][];
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = new int[dim2];
        }
        return matrix;
    }

    public static int[][][] createIntMatrix3(int dim1, int dim2, int dim3) {
        int[][][] matrix = new int[dim1][][];
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = new int[dim2][];
            for (int i2 = 0; i2 < dim2; i2++) {
                matrix[i1][i2] = new int[dim3];
            }
        }
        return matrix;
    }

    public static int[][][][] createIntMatrix4(int dim1, int dim2, int dim3, int dim4) {
        int[][][][] matrix = new int[dim1][][][];
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = new int[dim2][][];
            for (int i2 = 0; i2 < dim2; i2++) {
                matrix[i1][i2] = new int[dim3][];
                for (int i3 = 0; i3 < dim3; i3++) {
                    matrix[i1][i2][i3] = new int[dim4];
                }
            }
        }
        return matrix;
    }

    public static int[][][][] cloneIntMatrix4(int[][][][] mat) {
        int[][][][] newMat = new int[mat.length][][][];
        for (int x = 0; x < mat.length; x++) {
            newMat[x] = new int[mat[x].length][][];
            for (int y = 0; y < mat[x].length; y++) {
                newMat[x][y] = new int[mat[x][y].length][];
                for (int z = 0; z < mat[x][y].length; z++) {
                    newMat[x][y][z] = new int[mat[x][y][z].length];
                    System.arraycopy(mat[x][y][z], 0, newMat[x][y][z], 0, mat[x][y][z].length);
                }
            }
        }
        return newMat;
    }

    public static int[][] cloneIntMatrix2(int[][] mat) {
        int[][] newMat = new int[mat.length][];
        for (int x = 0; x < mat.length; x++) {
            newMat[x] = new int[mat[x].length];
            System.arraycopy(mat[x], 0, newMat[x], 0, mat[x].length);
        }
        return newMat;
    }

    public static int[][][] cloneIntMatrix3(int[][][] mat) {
        int[][][] newMat = new int[mat.length][][];
        for (int x = 0; x < mat.length; x++) {
            newMat[x] = new int[mat[x].length][];
            for (int y = 0; y < mat[x].length; y++) {
                newMat[x][y] = new int[mat[x][y].length];
                System.arraycopy(mat[x][y], 0, newMat[x][y], 0, mat[x][y].length);
            }
        }
        return newMat;
    }

    public static long[][][] createLongMatrix3(int dim1, int dim2, int dim3) {
        long[][][] matrix = new long[dim1][][];
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = new long[dim2][];
            for (int i2 = 0; i2 < dim2; i2++) {
                matrix[i1][i2] = new long[dim3];
            }
        }
        return matrix;
    }

    public static List<Long> iterateIntMatrix2ToIntList(int[][] matrix, IntMatrix2Iterator iterator) {
        List<Long> result = new ArrayList<>();
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                result.add(iterator.iterate(x, y, matrix[x][y]));
            }
        }
        return result;
    }

    public static List<Long> iterateIntMatrix4ToIntList(int[][][][] matrix, IntMatrix4Iterator iterator) {
        List<Long> result = new ArrayList<>();
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                for (int z = 0; z < matrix[x][y].length; z++) {
                    for (int w = 0; w < matrix[x][y][z].length; w++) {
                        result.add(iterator.iterate(x, y, z, w, matrix[x][y][z][w]));
                    }
                }
            }
        }
        return result;
    }

    public static long iterateIntMatrix2ToSum(int[][] matrix, IntMatrix2Iterator iterator) {
        return iterateIntMatrix2ToIntList(matrix, iterator).stream().reduce(0L, Long::sum);
    }

    public static long iterateIntMatrix2ToSumOfValues(int[][] matrix) {
        return iterateIntMatrix2ToIntList(matrix, (x, y, value) -> value).stream().reduce(0L, Long::sum);
    }

    public static long iterateIntMatrix4ToSum(int[][][][] matrix, IntMatrix4Iterator iterator) {
        return iterateIntMatrix4ToIntList(matrix, iterator).stream().reduce(0L, Long::sum);
    }

    public static void fillIntMatrix(int[][] matrix, int value) {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix.length; y++) {
                matrix[x][y] = value;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] createObjectMatrix2(int dim1, int dim2, Class<T> typeClass) {
        Class typeArrayClass = Array.newInstance(typeClass, 0).getClass();
        T[][] matrix = (T[][]) Array.newInstance(typeArrayClass, dim1);
        for (int i1 = 0; i1 < dim1; i1++) {
            matrix[i1] = (T[]) Array.newInstance(typeClass, dim2);
            for (int i2 = 0; i2 < dim2; i2++) {
                try {
                    matrix[i1][i2] = typeClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return matrix;
    }

    public static <T> void iterateObjectMatrix2(T[][] matrix, ObjectMatrix2Iterator<T> iterator) {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                iterator.iterate(x, y, matrix[x][y]);
            }
        }
    }

    public static <T> int inObjectMatrixFindLineWithAllElementsMatching(T[][] matrix, Predicate<T> predicate) {
        for (int line = 0; line < matrix.length; line++) {
            int elemsPerLine = 0;
            for (int col = 0; col < matrix[line].length; col++) {
                if (predicate.test(matrix[line][col])) {
                    elemsPerLine++;
                }
            }
            if (elemsPerLine == matrix[line].length) {
                return line;
            }
        }
        return -1;
    }

    public static <T> int inObjectMatrixFindColumnWithAllElementsMatching(T[][] matrix, Predicate<T> predicate) {
        for (int col = 0; col < matrix[0].length; col++) {
            int elemsPerCol = 0;
            for (int line = 0; line < matrix.length; line++) {
                if (predicate.test(matrix[line][col])) {
                    elemsPerCol++;
                }
            }
            if (elemsPerCol == matrix.length) {
                return col;
            }
        }
        return -1;
    }

    public static <T> void initObjectMatrixFromStringListWithInts(T[][] matrix, List<String> input, String inputDelimiter,
                                                                  ObjectMatrix2IntInitializer<T> initializer) {
        for (int line = 0; line < input.size(); line++) {
            List<Integer> numbersPerLine = XUtils.splitCsvStringToIntList(input.get(line), inputDelimiter);
            int numbersPerLineSize = numbersPerLine.size();
            for (int col = 0; col < numbersPerLineSize; col++) {
                initializer.initialize(line, col, matrix[line][col], numbersPerLine.get(col));
            }
        }
    }

    public static int countInIntMatrix2WhereValueIs(int value, int[][] matrix, int width, int height) {
        int result = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == value) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     *
     */
    public static String printIntMatrix2WithValueMappingToChar(int[][] matrix, int width, int height, Object... mapping) {
        if (mapping == null || mapping.length % 2 != 0) {
            throw new IllegalArgumentException("The mapping varargs must be a set of pairs value + character to print");
        }
        StringBuilder builder = new StringBuilder((width + 1) * height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int index = 0; index < mapping.length; index += 2) {
                    if (matrix[x][y] == (Integer) mapping[index]) {
                        builder.append(mapping[index + 1]);
                    }
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // ----------------------------------------------------------------------------------------------------

    public interface IntMatrix2Iterator {
        long iterate(int x, int y, int value);
    }

    public interface IntMatrix4Iterator {
        long iterate(int x, int y, int z, int w, int value);
    }

    public interface ObjectMatrix2Iterator<T> {
        void iterate(int x, int y, T obj);
    }

    public interface ObjectMatrix2IntInitializer<T> {
        void initialize(int x, int y, T obj, int intValue);
    }

}
