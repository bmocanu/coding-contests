package ws.bmocanu.aoc.flex;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ws.bmocanu.aoc.support.PosDelta4;
import ws.bmocanu.aoc.support.PosDelta8;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;

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

    public Collection<Point> allPointsOfType(int type) {
        return allPointsWhere(point -> point.type == type);
    }

    public Collection<Point> allPointsWithName(String name) {
        return allPointsWhere(point -> name.equals(point.name));
    }

    public Collection<Point> allPointsWhere(Predicate<? super Point> filterPredicate) {
        return pointMap
            .values()
            .stream()
            .filter(filterPredicate)
            .collect(Collectors.toList());
    }

    public Point pointWithMaxValue() {
        return pointMap.values().stream().max(Comparator.comparing(point -> point.value)).orElse(null);
    }

    public Point firstPointByType(int type) {
        return pointMap.values().stream().filter(point -> point.type == type).findFirst().orElse(null);
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

    public int countPointsWhere(Predicate<? super Point> filterPredicate) {
        return (int) pointMap.values().stream().filter(filterPredicate).count();
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

    public String toString(Function<Point, Object> pointPrinter, String strForMissingPoints, int padding) {
        StringBuilder builder = new StringBuilder((width + 1) * padding * (height + 2) + 100);
        builder.append("Width: [").append(width)
            .append("], Height: [").append(height)
            .append("], Points: [").append(pointMap.size())
            .append("]\n");
        String separator = "+" + "-".repeat(width * padding) + "+\n";
        builder.append(separator);
        for (int y = 0; y < height; y++) {
            builder.append('|');
            for (int x = 0; x < width; x++) {
                Point point = pointOrNull(x, y);
                if (point != null) {
                    builder.append(XUtils.strPadding(String.valueOf(pointPrinter.apply(point)), padding, " "));
                } else {
                    builder.append(XUtils.strPadding(strForMissingPoints, padding, " "));
                }
            }
            builder.append("|\n");
        }
        builder.append(separator);
        return builder.toString();
    }

    public String typesToString() {
        return toString(point -> point.type, " ", 1);
    }

    public String charactersToString() {
        return toString(point -> point.chr, " ", 1);
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
