package ws.bmocanu.aoc.utils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FlexStruct implements PointSupplier {

    private final Map<Long, Point> pointMap = new HashMap<>();
    private int positiveDelta = 10000;
    private int width;
    private int height;

    // ----------------------------------------------------------------------------------------------------

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

    public FlexCursor cursor(int x, int y) {
        return new FlexCursor(x, y, this);
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

    public Point pointByPoint(Point point) {
        return point(point.x, point.y);
    }

    public int size() {
        return pointMap.size();
    }

    public int width() {
        return width;
    }

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

    public FlexStruct setAllValuesTo(int value) {
        pointMap.values().forEach(point -> point.value = value);
        return this;
    }

    public FlexStruct setAllPointsAsMarked() {
        pointMap.values().forEach(Point::mark);
        return this;
    }

    public boolean pointExists(int x, int y) {
        return pointMap.get(getUniqueCoordsHash(x, y)) != null;
    }

    public int countPointsOfType(int type) {
        return countPointsWhere(point -> point.type == type);
    }

    public int countPointsWhere(Predicate<? super Point> filterPredicate) {
        return (int) pointMap.values().stream().filter(filterPredicate).count();
    }

    public FlexMappingProcessor mapDataInsidePoints() {
        return new FlexMappingProcessor(pointMap.values());
    }

    // ----------------------------------------------------------------------------------------------------

    public FlexStruct loadFromLineList(List<String> lineList) {
        for (int y = 0; y < lineList.size(); y++) {
            String currentLine = lineList.get(y);
            for (int x = 0; x < currentLine.length(); x++) {
                point(x, y).setCharacter(currentLine.charAt(x));
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
        });
        return newStruct;
    }

    public String printToString() {
        StringBuilder builder = new StringBuilder((width + 1) * height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point point = pointOrNull(x, y);
                if (point != null) {
                    builder.append(point.type);
                } else {
                    builder.append(' ');
                }
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    // ----------------------------------------------------------------------------------------------------

    private long getUniqueCoordsHash(int x, int y) {
        int deltaX = x + positiveDelta;
        int deltaY = y + positiveDelta;
        return Utils.getCantorPairingValue(deltaX, deltaY);
    }

//    func (matrix *FlexStruct) Print() {
//        fmt.Print("--------------------\n")
//        var point *Point
//        for y := 0; y < matrix.Height(); y++ {
//            fmt.Print("|")
//            for x := 0; x < matrix.Width(); x++ {
//                point = matrix.Point(x, y)
//                if point != nil {
//                    if point.Marked {
//                        fmt.Printf("%2s ", "#")
//                    } else if point.Value != 0 && !point.Destroyed {
//                        fmt.Printf("%2d ", point.Value)
//                    } else {
//                        fmt.Printf("%2s ", ".")
//                    }
//                } else {
//                    fmt.Printf("%2s ", ".")
//                }
//            }
//            fmt.Print("|\n")
//        }
//        fmt.Print("--------------------\n")
//    }
//
//    /*
//     TypeMapping = a CSV of key-Value items, to define the mapping when printing the content
//     "0, ,1,#" will result in all zeroes being printed as spaces, and all ones being printed as #
//     Special tokens that can be used:
//     - TMC = TrailMarkCount value
//     - VAL = Value of the node
//     - NME = The name of the node
//     - LOC = The location of the node
//    */
//    func (matrix *FlexStruct) PrintByType(typeMapping string, padding int) {
//        fmt.Print(matrix.internalPrintByType(typeMapping, padding, "\n", true))
//    }
//
//    func (matrix *FlexStruct) SerializeToString(typeMapping string, padding int) string {
//        return matrix.internalPrintByType(typeMapping, padding, "", false)
//    }
//
//// ----------------------------------------------------------------------------------------------------
//
//    func (matrix *FlexStruct) internalPrintByType(typeMapping string, padding int, lineSeparator string, decorate bool) string {
//        var typeMap = make(map[int]string)
//        if len(typeMapping) > 0 {
//            var typeMappingFragments = strings.Split(typeMapping, ",")
//            for index := 0; index < len(typeMappingFragments); index += 2 {
//                var leftValue, _ = strconv.Atoi(typeMappingFragments[index])
//                typeMap[leftValue] = typeMappingFragments[index+1]
//            }
//        }
//        var result = fmt.Sprintf("%s", lineSeparator)
//        if decorate {
//            result += fmt.Sprintf("+%s+%s", RepeatString("-", matrix.Width()*padding), lineSeparator)
//        }
//        var point *Point
//        for y := 0; y < matrix.Height(); y++ {
//            if decorate {
//                result += fmt.Sprint("|")
//            }
//            for x := 0; x < matrix.Width(); x++ {
//                point = matrix.PointOrNil(x, y)
//                if point != nil {
//                    var char, charFound = typeMap[point.Type]
//                    if charFound {
//                        switch char {
//                            case "TMC":
//                                result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.TrailMarkCount)
//                            case "VAL":
//                                result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.Value)
//                            case "NME":
//                                result += fmt.Sprintf("%"+strconv.Itoa(padding)+"s", point.Name)
//                            case "LOC":
//                                result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.Location)
//                            default:
//                                result += fmt.Sprintf("%"+strconv.Itoa(padding)+"s", char)
//                        }
//                    } else {
//                        result += fmt.Sprint(RepeatString("?", padding))
//                    }
//                } else {
//                    result += fmt.Sprint(RepeatString(" ", padding))
//                }
//            }
//            if decorate {
//                result += fmt.Sprint("|")
//            }
//            result += fmt.Sprintf("%s", lineSeparator)
//        }
//
//        if decorate {
//            result += fmt.Sprintf("+%s+", RepeatString("-", matrix.Width()*padding))
//        }
//        result += fmt.Sprintf("%s", lineSeparator)
//        return result
//    }

    // ----------------------------------------------------------------------------------------------------

    public static class FlexMappingProcessor {

        private final Collection<Point> points;

        public FlexMappingProcessor(Collection<Point> points) {
            this.points = points;
        }

        public FlexMappingProcessor charToType(char character, int type) {
            points.forEach(point -> {
                if (point.character == character) {
                    point.type = type;
                }
            });
            return this;
        }

    }

}
