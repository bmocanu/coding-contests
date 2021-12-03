package ws.bmocanu.aoc.xbase;

import java.util.Objects;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;

public class SolutionBase {

    private static String forcedFilePath = null;

    public static void setForcedFilePath(String forcedFilePath) {
        SolutionBase.forcedFilePath = forcedFilePath;
    }

    protected static String filePath(String name) {
        Log.reset();
        return Objects.requireNonNullElseGet(forcedFilePath, () -> XRead.sourceFileName(name));
    }

}
