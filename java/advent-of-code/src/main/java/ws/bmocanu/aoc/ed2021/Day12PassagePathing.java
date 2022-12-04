package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day12PassagePathing extends SolutionBase {

    static Map<String, List<String>> graph = new HashMap<>();
    static int pathCount = 0;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day12"));
        for (String line : input) {
            String[] lineParts = line.split("-");
            for (int index = 0; index < 2; index++) {
                List<String> connections = graph.get(lineParts[index]);
                if (connections == null) {
                    connections = new ArrayList<>();
                    graph.put(lineParts[index], connections);
                }
                connections.add(lineParts[1 - index]);
            }
        }

        visit("start", new LinkedList<>(), false, false);
        Log.part1(pathCount);

        pathCount = 0;
        visit("start", new LinkedList<>(), true, false);
        Log.part2(pathCount);
    }

    public static void visit(String node, List<String> pathSoFar,
                             boolean visitSmallCavesTwice, boolean aSmallCaveIsTwiceAlready) {
        if ("end".equals(node)) {
            pathCount++;
            return;
        }
        List<String> connections = graph.get(node);
        for (String con : connections) {
            if (!"start".equals(con)) {
                if (con.equals(con.toUpperCase()) || !pathSoFar.contains(con)) {
                    pathSoFar.add(con);
                    visit(con, pathSoFar, visitSmallCavesTwice, aSmallCaveIsTwiceAlready);
                    pathSoFar.remove(pathSoFar.size() - 1);
                } else {
                    if (visitSmallCavesTwice && !aSmallCaveIsTwiceAlready) {
                        pathSoFar.add(con);
                        visit(con, pathSoFar, visitSmallCavesTwice, true);
                        pathSoFar.remove(pathSoFar.size() - 1);
                    }
                }
            }
        }
    }

}
