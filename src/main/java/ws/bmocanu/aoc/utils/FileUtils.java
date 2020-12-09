package ws.bmocanu.aoc.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ws.bmocanu.aoc.support.Constants;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FileUtils {

    public static List<Integer> fileAsIntPerLineToIntList(String fileName) {
        String filePath = sourceFileName(fileName);
        List<Integer> intList = new ArrayList<>();
        try (InputStream is = Utils.class.getResourceAsStream(filePath)) {
            List<String> resultList = new ArrayList<>();
            LineIterator lineIterator = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                intList.add(Integer.parseInt(lineIterator.nextLine()));
            }
            return intList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file [" + filePath + "]", e);
        }
    }

    public static List<Long> fileAsLongPerLineToLongList(String fileName) {
        String filePath = sourceFileName(fileName);
        List<Long> intList = new ArrayList<>();
        try (InputStream is = Utils.class.getResourceAsStream(filePath)) {
            List<String> resultList = new ArrayList<>();
            LineIterator lineIterator = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                intList.add(Long.parseLong(lineIterator.nextLine()));
            }
            return intList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file [" + filePath + "]", e);
        }
    }

    public static List<Integer> fileAsCsvLineToIntList(String fileName, String separator) {
        String fileContent = fileToOneString(fileName);
        StringTokenizer tokenizer = new StringTokenizer(fileContent, separator);
        List<Integer> intList = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            intList.add(Integer.parseInt(tokenizer.nextToken().trim()));
        }
        return intList;
    }

    public static List<String> fileAsCsvLineToStringList(String fileName, String separator) {
        String fileContent = fileToOneString(fileName);
        StringTokenizer tokenizer = new StringTokenizer(fileContent, separator);
        List<String> stringList = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            stringList.add(tokenizer.nextToken().trim());
        }
        return stringList;
    }

    public static String fileToOneString(String fileName) {
        String filePath = sourceFileName(fileName);
        try (InputStream is = Utils.class.getResourceAsStream(filePath)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file [" + filePath + "]", e);
        }
    }

    public static List<String> fileAsStringPerLineToStringList(String fileName) {
        String filePath = sourceFileName(fileName);
        try (InputStream is = Utils.class.getResourceAsStream(filePath)) {
            List<String> resultList = new ArrayList<>();
            LineIterator lineIterator = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                resultList.add(lineIterator.nextLine());
            }
            return resultList;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file [" + filePath + "]", e);
        }
    }

    public static char[][] fileAsCharMatrixToCharMatrix(String fileName) {
        List<String> lineList = fileAsStringPerLineToStringList(fileName);
        int maxWidth = lineList.stream().mapToInt(String::length).max().orElse(0);
        char[][] resultMatrix = new char[lineList.size()][maxWidth];
        for (int row = 0; row < lineList.size(); row++) {
            String currentLine = lineList.get(row);
            for (int col = 0; col < maxWidth; col++) {
                if (col < currentLine.length()) {
                    resultMatrix[row][col] = currentLine.charAt(col);
                } else {
                    resultMatrix[row][col] = ' ';
                }
            }
        }
        return resultMatrix;
    }

    public static int[] fileAsLongDigitsLineToIntArray(String fileName) {
        String fileContent = fileToOneString(fileName);
        int[] result = new int[fileContent.length()];
        for (int index = 0; index < fileContent.length(); index++) {
            result[index] = ((int) fileContent.charAt(index) - (int) '0');
        }
        return result;
    }

    public static void printStringToFile(String content, String fileName) {
        ensureOutputFolderExists();
        String filePath = outputFileName(fileName);
        try {
            File file = new File(filePath);
            try (OutputStream os = new FileOutputStream(file)) {
                IOUtils.write(content, os, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save to file [" + filePath + "]", e);
        }
    }

    public static String generateTimestampString() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return localTime.format(formatter);
    }

    public static File getTimestampedOutputFile(String filePrefix) {
        ensureOutputFolderExists();
        String filePath = outputFileName(filePrefix + "-" + generateTimestampString() + ".txt");
        return new File(filePath);
    }

    // ----------------------------------------------------------------------------------------------------

    private static String sourceFileName(String fileName) {
        String finalFileName = fileName;
        if (!fileName.contains(".")) {
            finalFileName = finalFileName + ".txt";
        }
        return "/" + Constants.EDITION + "/" + finalFileName;
    }

    private static String outputFileName(String fileName) {
        String finalFileName = fileName;
        if (!fileName.contains(".")) {
            finalFileName = finalFileName + ".txt";
        }
        return Constants.OUTPUT_FOLDER + "/" + finalFileName;
    }

    private static void ensureOutputFolderExists() {
        File outputFolder = new File(Constants.OUTPUT_FOLDER);
        if (!outputFolder.exists()) {
            if (!outputFolder.mkdirs()) {
                throw new RuntimeException("Cannot create the output folder: " + outputFolder.getAbsolutePath());
            }
        }
    }

}
