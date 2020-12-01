package ws.bmocanu.aoc.ed2020;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import ws.bmocanu.aoc.utils.FlexStruct;
import ws.bmocanu.aoc.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day01 {

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();

        String fileName = "/ed2020/day01.txt";
        try (InputStream is = Utils.class.getResourceAsStream(fileName)) {
            List<String> resultList = new ArrayList<>();
            LineIterator lineIterator = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                intList.add(Integer.parseInt(lineIterator.nextLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load payload from file [" + fileName + "]", e);
        }

        for (int index1 = 0; index1 < intList.size(); index1++) {
            for (int index2 = 0; index2 < index1; index2++) {
                for (int index3 = 0; index3 < index2; index3++) {
                    if (intList.get(index1) + intList.get(index2) + intList.get(index3) == 2020) {
                        System.out.println(intList.get(index1) * intList.get(index2) * intList.get(index3));
                    }
                }
            }
        }

    }

}
