package ws.bmocanu.aoc.flex;

import org.apache.commons.codec.digest.DigestUtils;
import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FlexStruct implements PointSupplier {

    private final Map<Long, Point> pointMap = new HashMap<>();
    private int positiveDelta = 10000;
    private int width;
    private int height;

    // ----------------------------------------------------------------------------------------------------

    public static FlexStruct fromLineList(List<String> lineList) {
        FlexStruct struct = new FlexStruct();
        struct.loadFromLineList(lineList);
        return struct;
    }

    public static FlexStruct fromLineListWithShift(List<String> lineList, int shiftX, int shiftY) {
        FlexStruct struct = new FlexStruct();
        struct.loadFromLineListWithShift(lineList, shiftX, shiftY);
        return struct;
    }

    public static FlexStruct fromFile(String fileName) {
        FlexStruct struct = new FlexStruct();
        struct.loadFromFile(fileName);
        return struct;
    }

    // ----------------------------------------------------------------------------------------------------

    public Point point(Point point) {
        return point(point.x, point.y);
    }

    @Override
    public Point point(int x, int y) {
        long uniqueCoordsHash = getUniqueCoordsHash(x, y);
        Point point = pointMap.get(uniqueCoordsHash);
        if (point == null) {
            point = Point.from(x, y);
            pointMap.put(uniqueCoordsHash, point);
            if (x >= width) {
                width = x + 1;
            }
            if (y >= height) {
                height = y + 1;
            }
        }
        return point;
    }

    public Point point(int x, int y, PosDelta4 deltaDir) {
        return point(x + deltaDir.deltaX, y + deltaDir.deltaY);
    }

    public Point point(int x, int y, PosDelta8 deltaDir) {
        return point(x + deltaDir.deltaX, y + deltaDir.deltaY);
    }

    public Point point(Point p, PosDelta4 deltaDir) {
        return point(p.x + deltaDir.deltaX, p.y + deltaDir.deltaY);
    }

    public Point point(Point p, PosDelta8 deltaDir) {
        return point(p.x + deltaDir.deltaX, p.y + deltaDir.deltaY);
    }

    public Cursor cursor(int x, int y) {
        return new Cursor(x, y, this);
    }

    public int valueOrZero(int x, int y) {
        Point point = point(x, y);
        if (point == null) {
            return 0;
        }
        return point.value;
    }

    @Override
    public Point pointOrNull(int x, int y) {
        return pointMap.get(getUniqueCoordsHash(x, y));
    }

    public Point pointOrNull(Point point) {
        return pointMap.get(getUniqueCoordsHash(point.x, point.y));
    }

    public Point pointOrNull(Point point, PosDelta4 deltaDir) {
        return pointMap.get(getUniqueCoordsHash(point.x + deltaDir.deltaX, point.y + deltaDir.deltaY));
    }

    public Point pointOrNull(Point point, PosDelta8 deltaDir) {
        return pointMap.get(getUniqueCoordsHash(point.x + deltaDir.deltaX, point.y + deltaDir.deltaY));
    }

    public int lowestX() {
        int minX = Integer.MAX_VALUE;
        for (Point p : allPoints()) {
            if (p.x < minX) {
                minX = p.x;
            }
        }
        return minX;
    }

    public int highestX() {
        int maxX = Integer.MIN_VALUE;
        for (Point p : allPoints()) {
            if (p.x > maxX) {
                maxX = p.x;
            }
        }
        return maxX;
    }

    public int lowestY() {
        int minY = Integer.MAX_VALUE;
        for (Point p : allPoints()) {
            if (p.y < minY) {
                minY = p.y;
            }
        }
        return minY;
    }

    public int highestY() {
        int maxY = Integer.MIN_VALUE;
        for (Point p : allPoints()) {
            if (p.y > maxY) {
                maxY = p.y;
            }
        }
        return maxY;
    }

    public Point pointByPoint(Point point) {
        return point(point.x, point.y);
    }

    public int size() {
        return pointMap.size();
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    public Collection<Point> allPoints() {
        return pointMap.values();
    }

    public Collection<Point> allPointsMarked() {
        return allPointsWhere(point -> point.marked);
    }

    public Collection<Point> allPointsMarkedAndUnvisited() {
        return allPointsWhere(point -> point.marked && !point.visited);
    }

    public Collection<Point> allPointsUnmarked() {
        return allPointsWhere(point -> !point.marked);
    }

    public Collection<Point> allPointsUnvisited() {
        return allPointsWhere(point -> !point.visited);
    }

    public List<Point> allPointsOfType(int type) {
        return allPointsWhere(point -> point.type == type);
    }

    public Collection<Point> allPointsOfValue(int value) {
        return allPointsWhere(point -> point.value == value);
    }

    public Collection<Point> allPointsOfChar(char c) {
        return allPointsWhere(point -> point.chr == c);
    }

    public Collection<Point> allPointsWithName(String name) {
        return allPointsWhere(point -> name.equals(point.name));
    }

    public List<Point> allPointsWhere(Predicate<? super Point> filterPredicate) {
        return pointMap
                .values()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toList());
    }

    public int highestYForPointsOfType(int type) {
        int minY = Integer.MIN_VALUE;
        boolean valueChanged = false;
        for (Point point : pointMap.values()) {
            if (point.type == type && point.y > minY) {
                minY = point.y;
                valueChanged = true;
            }
        }
        return valueChanged ? minY : 0;
    }

    public int highestYForXAndPointsOfType(int x, int type) {
        int minY = Integer.MIN_VALUE;
        boolean valueChanged = false;
        for (Point point : pointMap.values()) {
            if (point.type == type && point.x == x && point.y > minY) {
                minY = point.y;
                valueChanged = true;
            }
        }
        return valueChanged ? minY : 0;
    }

    public Point pointWithMaxValue() {
        return pointMap.values().stream().max(Comparator.comparing(point -> point.value)).orElse(null);
    }

    public Point firstPointByType(int type) {
        return pointMap.values().stream().filter(point -> point.type == type).findFirst().orElse(null);
    }

    public Point firstPointByChar(char chr) {
        return pointMap.values().stream().filter(point -> point.chr == chr).findFirst().orElse(null);
    }

    public FluentPointsActions forAllPoints() {
        return new FluentPointsActions();
    }

    public boolean pointExists(int x, int y) {
        return pointMap.get(getUniqueCoordsHash(x, y)) != null;
    }

    public boolean pointExists(Point point, PosDelta4 deltaDir) {
        return pointMap.get(getUniqueCoordsHash(point.x + deltaDir.deltaX, point.y + deltaDir.deltaY)) != null;
    }

    public int countPointsOfType(int type) {
        return countPointsWhere(point -> point.type == type);
    }

    public int countPointsWithValue(int value) {
        return countPointsWhere(point -> point.value == value);
    }

    public int countPointsWhere(Predicate<? super Point> filterPredicate) {
        return (int) pointMap.values().stream().filter(filterPredicate).count();
    }

    public int countPointsMarked() {
        return countPointsWhere(point -> point.marked);
    }

    public FlexStruct forEachPoint(Consumer<Point> consumer) {
        pointMap.values().forEach(consumer);
        return this;
    }

    public FlexStruct forEachPointMarked(Consumer<Point> consumer) {
        pointMap.values().stream().filter(point -> point.marked).forEach(consumer);
        return this;
    }

    public FlexStruct forEachPointWithType(int type, Consumer<Point> consumer) {
        pointMap.values().stream().filter(point -> point.type == type).forEach(consumer);
        return this;
    }

    public FlexStruct forEachPointWithTypeDifferentThan(int type, Consumer<Point> consumer) {
        pointMap.values().stream().filter(point -> point.type != type).forEach(consumer);
        return this;
    }

    public FlexStruct forEachPointWithChr(char chr, Consumer<Point> consumer) {
        pointMap.values().stream().filter(point -> point.chr == chr).forEach(consumer);
        return this;
    }

    public FlexStruct forEachPointWithChrAsLetter(Consumer<Point> consumer) {
        pointMap.values().stream().filter(point -> XUtils.charIsLetter(point.chr)).forEach(consumer);
        return this;
    }

    // ----------------------------------------------------------------------------------------------------

    public FlexStruct loadFromFile(String fileName) {
        return loadFromLineList(XRead.fileAsStringPerLineToStringList(fileName));
    }

    public FlexStruct loadFromLineList(List<String> lineList) {
        for (int y = 0; y < lineList.size(); y++) {
            String currentLine = lineList.get(y);
            for (int x = 0; x < currentLine.length(); x++) {
                point(x, y).setChr(currentLine.charAt(x));
            }
        }
        return this;
    }

    public FlexStruct loadFromLineListWithShift(List<String> lineList, int shiftX, int shiftY) {
        for (int y = 0; y < lineList.size(); y++) {
            String currentLine = lineList.get(y);
            for (int x = 0; x < currentLine.length(); x++) {
                point(x + shiftX, y + shiftY).setChr(currentLine.charAt(x));
            }
        }
        return this;
    }

    public FlexStruct deepClone() {
        FlexStruct newStruct = new FlexStruct();
        newStruct.width = width;
        newStruct.height = height;
        newStruct.positiveDelta = positiveDelta;
        for (Map.Entry<Long, Point> entry : pointMap.entrySet()) {
            newStruct.pointMap.put(entry.getKey(), entry.getValue().deepClone());
        }
        pointMap.values().forEach(point -> {
            if (point.link != null) {
                Point newPoint = newStruct.pointByPoint(point);
                newPoint.link = newStruct.pointByPoint(point.link);
            }
            if (point.pathLink != null) {
                Point newPoint = newStruct.pointByPoint(point);
                newPoint.pathLink = newStruct.pointByPoint(point.pathLink);
            }
        });
        return newStruct;
    }

    public String toString(Function<Point, Object> pointPrinter,
                           String strForMissingPoints,
                           int padding,
                           boolean printHeader, boolean printNewLines, boolean printBorders) {
        StringBuilder builder = new StringBuilder((width + 1) * padding * (height + 2) + 100);
        if (printHeader) {
            builder.append("Width: [").append(width)
                    .append("], Height: [").append(height)
                    .append("], Points: [").append(pointMap.size())
                    .append("]\n");
        }
        String separator = null;
        if (printBorders) {
            separator = "+" + "-".repeat(width * padding) + "+\n";
            builder.append(separator);
        }
        for (int y = 0; y < height; y++) {
            if (printBorders) {
                builder.append('|');
            }
            for (int x = 0; x < width; x++) {
                Point point = pointOrNull(x, y);
                if (point != null) {
                    builder.append(XUtils.strPadding(String.valueOf(pointPrinter.apply(point)), padding, " "));
                } else {
                    builder.append(XUtils.strPadding(strForMissingPoints, padding, " "));
                }
            }
            if (printBorders) {
                builder.append('|');
            }
            if (printBorders || printNewLines) {
                builder.append('\n');
            }
        }
        if (printBorders) {
            builder.append(separator);
        }
        return builder.toString();
    }

    public String toString2(Function<Point, Object> pointPrinter,
                            String strForMissingPoints,
                            int padding,
                            boolean printHeader, boolean printNewLines, boolean printBorders) {
        StringBuilder builder = new StringBuilder((width + 1) * padding * (height + 2) + 100);
        if (printHeader) {
            builder.append("Width: [").append(width)
                    .append("], Height: [").append(height)
                    .append("], Points: [").append(pointMap.size())
                    .append("]\n");
        }
        String separator = null;
        if (printBorders) {
            separator = "+" + "-".repeat(width * padding) + "+\n";
            builder.append(separator);
        }
        int minX = 0;
        int minY = 0;
        for (Point p : allPoints()) {
            if (p.x < minX) {
                minX = p.x;
            }
            if (p.y < minY) {
                minY = p.y;
            }
        }

        for (int y = minY; y < height; y++) {
            if (printBorders) {
                builder.append('|');
            }
            for (int x = minX; x < width; x++) {
                Point point = pointOrNull(x, y);
                if (point != null) {
                    builder.append(XUtils.strPadding(String.valueOf(pointPrinter.apply(point)), padding, " "));
                } else {
                    builder.append(XUtils.strPadding(strForMissingPoints, padding, " "));
                }
            }
            if (printBorders) {
                builder.append('|');
            }
            if (printBorders || printNewLines) {
                builder.append('\n');
            }
        }
        if (printBorders) {
            builder.append(separator);
        }
        return builder.toString();
    }

    public String typesToString() {
        return toString(point -> point.type, " ", 1, false, true, false);
    }

    public String typesToString2() {
        return toString2(point -> point.type, " ", 1, false, true, false);
    }

    public String charactersToString() {
        return toString(point -> point.chr, " ", 1, false, true, false);
    }

    public String charactersToString2() {
        return toString2(point -> point.chr, " ", 1, false, true, false);
    }

    public String valuesAsDigitsToString() {
        return toString(point -> point.value, " ", 1, false, true, false);
    }

    public String toSha256(Function<Point, Object> pointPrinter) {
        String strForMissingPoints = "/";
        StringBuilder builder = new StringBuilder(width * height + 10);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point point = pointOrNull(x, y);
                if (point != null) {
                    builder.append(pointPrinter.apply(point));
                } else {
                    builder.append(strForMissingPoints);
                }
            }
        }
        return DigestUtils.sha256Hex(builder.toString());
    }

    public String charactersToSha256() {
        return toSha256(point -> point.chr);
    }

    // ----------------------------------------------------------------------------------------------------

    private long getUniqueCoordsHash(int x, int y) {
        int deltaX = x + positiveDelta;
        int deltaY = y + positiveDelta;
        return XUtils.getCantorPairingValue(deltaX, deltaY);
    }

    // ----------------------------------------------------------------------------------------------------

    public static class FluentMappingActions {

        private final Collection<Point> points;

        public FluentMappingActions(Collection<Point> points) {
            this.points = points;
        }

        public FluentMappingActions charToType(char character, int type) {
            points.forEach(point -> {
                if (point.chr == character) {
                    point.type = type;
                }
            });
            return this;
        }

        public FluentMappingActions typeToChar(int type, char character) {
            points.forEach(point -> {
                if (point.type == type) {
                    point.chr = character;
                }
            });
            return this;
        }

        public FluentMappingActions charToValue(char character, int value) {
            points.forEach(point -> {
                if (point.chr == character) {
                    point.value = value;
                }
            });
            return this;
        }

        public FluentMappingActions charAsDigitToValue() {
            points.forEach(point -> {
                point.value = point.chr - '0';
            });
            return this;
        }

        public FluentMappingActions anyLetterToType(int type) {
            points.forEach(point -> {
                if (XUtils.charIsLetter(point.chr)) {
                    point.type = type;
                }
            });
            return this;
        }

    }

    public class FluentPointsActions {

        public FluentPointsActions setMarked() {
            pointMap.values().forEach(Point::mark);
            return this;
        }

        public FluentPointsActions mark() {
            setMarked();
            return this;
        }

        public FluentPointsActions unmark() {
            pointMap.values().forEach(point -> point.marked = false);
            return this;
        }

        public FluentPointsActions unvisit() {
            pointMap.values().forEach(point -> point.visited = false);
            return this;
        }

        public FluentPointsActions clearPathLink() {
            pointMap.values().forEach(point -> point.setPathLink(null));
            return this;
        }

        public FluentPointsActions clearPathCount() {
            pointMap.values().forEach(point -> point.setPathCount(0));
            return this;
        }

        public FluentPointsActions setPathCount(int pathCount) {
            pointMap.values().forEach(point -> point.setPathCount(pathCount));
            return this;
        }

        public FluentPointsActions setValueTo(int value) {
            pointMap.values().forEach(point -> point.value = value);
            return this;
        }

        public FluentPointsActions setTypeTo(int type) {
            pointMap.values().forEach(point -> point.type = type);
            return this;
        }

        public FluentMappingActions mapData() {
            return new FluentMappingActions(pointMap.values());
        }

    }

}
