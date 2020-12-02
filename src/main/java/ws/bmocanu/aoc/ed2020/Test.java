package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.flex.FlexNumber;
import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.path.PathAdvisor;
import ws.bmocanu.aoc.path.PathMarker;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.FileUtils;

import java.util.List;

public class Test {

    static int typeWall = 1;
    static int typeSpace = 0;

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList("test2.txt");
        FlexStruct struct = FlexStruct.fromLineList(stringLines);
        struct.forAllPoints()
                .setTypeTo(typeWall)
                .mapData().charToType('.', typeSpace);
        PathMarker pathMarker = new PathMarker(struct, new PathAdvisor() {
            @Override
            public boolean isFreeToWalk(Point point) {
                return point.type == typeSpace;
            }
        });
        pathMarker.startFrom(3, 5);
        System.out.println(struct.print((point) -> point.pathCount, " ", 4));
    }

}
