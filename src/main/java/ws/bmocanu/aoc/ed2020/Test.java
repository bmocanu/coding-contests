package ws.bmocanu.aoc.ed2020;

import java.util.List;

import ws.bmocanu.aoc.support.FlexStruct;
import ws.bmocanu.aoc.utils.FileUtils;

public class Test {

    private static final int typeWall = 1;
    private static final int typeSpace = 2;
    private static final int typeLetter = 7;

    public static void main(String[] args) {
        List<String> inputLines = FileUtils.fileAsStringPerLineToStringList("test2");
        FlexStruct flexStruct = FlexStruct.fromLineList(inputLines);
        flexStruct
            .mapDataInsidePoints()
            .charToType('#', typeWall)
            .charToType('.', typeSpace)
            .anyLetterToType(typeLetter);
        FileUtils.printStringToFile(flexStruct.print((point) -> point.chr, ' '), "test1");
    }

}
