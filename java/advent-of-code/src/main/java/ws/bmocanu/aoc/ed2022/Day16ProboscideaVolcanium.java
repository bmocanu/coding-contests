package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day16ProboscideaVolcanium extends SolutionBase {

    private static final Map<String, Valve> valves = new HashMap<>();
    private static Map<String, Integer> pathPressureMap;
    private static List<String> openedValves;
    private static Valve aaValve;

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day16"));
        List<ValveInput> valveInput = new SBind<>("Valve (\\w+) has flow rate=(-?\\d+); tunnels? leads? to valves? ([\\w,\\s]+)",
                ValveInput.class, "name", "pressure", "childrenString").bindAll(input);
        for (ValveInput vInput : valveInput) {
            Valve v = getValve(vInput.name);
            v.pressure = vInput.pressure;
            for (String childName : vInput.childrenString.split(", ")) {
                v.children.add(getValve(childName));
            }
        }

        aaValve = getValve("AA");

        pathPressureMap = new HashMap<>();
        openedValves = new LinkedList<>();
        Log.part1(calculatePressure(aaValve, 30, false));

        pathPressureMap = new HashMap<>();
        openedValves = new LinkedList<>();
        Log.part2(calculatePressure(aaValve, 26, true));
    }

    private static int calculatePressure(Valve valve, int minute, boolean workingWithElephant) {
        if (minute <= 0) {
            if (!workingWithElephant) {
                return 0;
            } else {
                return calculatePressure(aaValve, 26, false);
            }
        }
        Integer preCalculatedPressure = pathPressureMap.get(
                serialize(valve, minute, openedValves, workingWithElephant));
        if (preCalculatedPressure != null) {
            return preCalculatedPressure;
        }

        int currentPressure = 0;
        if (valve.pressure > 0 && !openedValves.contains(valve.name)) {
            openedValves.add(valve.name);
            currentPressure = valve.pressure * (minute - 1) + calculatePressure(valve, minute - 1, workingWithElephant);
            openedValves.remove(valve.name);
        }

        for (Valve otherValve : valve.children) {
            currentPressure = Math.max(currentPressure, calculatePressure(otherValve, minute - 1, workingWithElephant));
        }
        pathPressureMap.put(serialize(valve, minute, openedValves, workingWithElephant), currentPressure);

        return currentPressure;
    }

    private static String serialize(Valve valve, int minute, List<String> openedValveNames, boolean workingWithElephant) {
        StringBuilder builder = new StringBuilder(30);
        builder.append(valve.name).append(',');
        builder.append(minute).append(',');
        openedValveNames.stream().sorted().forEach(name -> builder.append(name).append(','));
        builder.append(workingWithElephant);
        return builder.toString();
    }

    private static Valve getValve(String name) {
        Valve result = valves.get(name);
        if (result == null) {
            result = new Valve();
            result.name = name;
            valves.put(name, result);
        }
        return result;
    }

    // ----------------------------------------------------------------------------------------------------

    public static class ValveInput {
        public String name;
        public int pressure;
        public String childrenString;
    }

    private static class Valve {
        public String name;
        public int pressure;
        public List<Valve> children = new ArrayList<>();
    }

}
