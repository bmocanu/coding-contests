package ws.bmocanu.aoc.utils;

import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class XList {

    public static void iterateWith2IntCursors(List<Integer> intList, ProcessorFor2Ints processor) {
        for (int index1 = 0; index1 < intList.size(); index1++) {
            for (int index2 = 0; index2 < index1; index2++) {
                processor.process(intList.get(index1), intList.get(index2));
            }
        }
    }

    public static void iterateWith3IntCursors(List<Integer> intList, ProcessorFor3Ints processor) {
        for (int index1 = 0; index1 < intList.size(); index1++) {
            for (int index2 = 0; index2 < index1; index2++) {
                for (int index3 = 0; index3 < index2; index3++) {
                    processor.process(intList.get(index1), intList.get(index2), intList.get(index3));
                }
            }
        }
    }

    // ----------------------------------------------------------------------------------------------------

    @FunctionalInterface
    public interface ProcessorFor2Ints {
        void process(int nr1, int nr2);
    }

    @FunctionalInterface
    public interface ProcessorFor3Ints {
        void process(int nr1, int nr2, int nr3);
    }

}
