package ws.bmocanu.aoc.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
