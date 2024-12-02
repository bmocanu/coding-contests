package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.SList;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day19NotEnoughMinerals extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day19"));
        List<Blueprint> blueprintList =
                new SBind<>("Blueprint (-?\\d+): " +
                        "Each ore robot costs (-?\\d+) ore. " +
                        "Each clay robot costs (-?\\d+) ore. " +
                        "Each obsidian robot costs (-?\\d+) ore and (-?\\d+) clay. " +
                        "Each geode robot costs (-?\\d+) ore and (-?\\d+) obsidian.",
                        Blueprint.class, "id", "oreRobotCostInOre", "clayRobotCostInOre",
                        "obsidianRobotCostInOre", "obsidianRobotCostInClay", "geodeRobotCostInOre",
                        "geodeRobotCostInObsidian").bindAll(input);

//        int blueprintSum = 0;
        for (Blueprint blueprint : blueprintList) {
            blueprint.init();
//            int geodes = calculateGeodes(blueprint);
//            System.out.println(blueprint.id + " = " + geodes);
//            blueprintSum += blueprint.id * geodes;
        }
//
//        Log.part1(blueprintSum);
//        Log.part2(0);
        System.out.println(calculateGeodes(blueprintList.get(0)));
    }

    // ----------------------------------------------------------------------------------------------------

    private static int calculateGeodes(Blueprint blueprint) {
        Set<FlowState> visitedStates = new HashSet<>();
        SList<FlowState> statesToProcess = new SList<>();
        statesToProcess.add(new FlowState(0, 0, 0, 0, 0,
                1, 0, 0, 0));
        int maxGeode = 0;
        while (!statesToProcess.isEmpty()) {
            System.out.println(statesToProcess.size() + " - " + visitedStates.size());
            FlowState currentState = statesToProcess.popFirst();
            visitedStates.add(currentState);
            if (currentState.depth < 24) {
                for (FlowState nextState : currentState.getNextStates(blueprint)) {
                    if (nextState.getPotentialGeodeToProduce() > maxGeode && !visitedStates.contains(nextState)) {
                        statesToProcess.add(nextState);
                    }
                }
            } else if (currentState.depth == 24) {
                maxGeode = Math.max(maxGeode, currentState.geode);
            }
        }
        return maxGeode;
    }


    // ----------------------------------------------------------------------------------------------------

    public static class Blueprint {
        int id;
        int oreRobotCostInOre;
        int clayRobotCostInOre;
        int obsidianRobotCostInOre;
        int obsidianRobotCostInClay;
        int geodeRobotCostInOre;
        int geodeRobotCostInObsidian;

        int maxOreNeeded;
        int maxClayNeeded;
        int maxObsNeeded;

        public void init() {
            maxOreNeeded = XUtils.maxFromVarargs(oreRobotCostInOre, clayRobotCostInOre, obsidianRobotCostInOre, geodeRobotCostInOre).value * 3;
            maxClayNeeded = obsidianRobotCostInClay * 3;
            maxObsNeeded = geodeRobotCostInObsidian * 3;
        }
    }

    private static class FlowState {
        int depth;

        int ore;
        int clay;
        int obs;
        int geode;

        int oreRobots;
        int clayRobots;
        int obsRobots;
        int geodeRobots;

        public FlowState(int depth, int ore, int clay, int obs, int geode, int oreRobots, int clayRobots, int obsRobots, int geodeRobots) {
            this.depth = depth;
            this.ore = ore;
            this.clay = clay;
            this.obs = obs;
            this.geode = geode;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsRobots = obsRobots;
            this.geodeRobots = geodeRobots;
        }

        public List<FlowState> getNextStates(Blueprint blueprint) {
            List<FlowState> result = new ArrayList<>();
            result.add(new FlowState(depth + 1, ore + oreRobots,
                    clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                    oreRobots, clayRobots, obsRobots, geodeRobots));

            if (ore >= blueprint.geodeRobotCostInOre && obs >= blueprint.geodeRobotCostInObsidian) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.geodeRobotCostInOre,
                        clay + clayRobots, obs + obsRobots - blueprint.geodeRobotCostInObsidian, geode + geodeRobots,
                        oreRobots, clayRobots, obsRobots, geodeRobots + 1));
            }
            if (obs < blueprint.maxObsNeeded &&
                    ore >= blueprint.obsidianRobotCostInOre && clay >= blueprint.obsidianRobotCostInClay) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.obsidianRobotCostInOre,
                        clay + clayRobots - blueprint.obsidianRobotCostInClay, obs + obsRobots, geode + geodeRobots,
                        oreRobots, clayRobots, obsRobots + 1, geodeRobots));
            }
            if (clay < blueprint.maxClayNeeded && ore >= blueprint.clayRobotCostInOre) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.clayRobotCostInOre,
                        clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                        oreRobots, clayRobots + 1, obsRobots, geodeRobots));
            }
            if (ore < blueprint.maxOreNeeded && ore >= blueprint.oreRobotCostInOre) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.oreRobotCostInOre,
                        clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                        oreRobots + 1, clayRobots, obsRobots, geodeRobots));
            }
            return result;
        }

        public int getPotentialGeodeToProduce() {
            int minutesLeft = 24 - depth;
            return (minutesLeft * (minutesLeft + 1) / 2) + minutesLeft * geodeRobots + geode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FlowState flowState = (FlowState) o;
            return depth == flowState.depth && ore == flowState.ore && clay == flowState.clay && obs == flowState.obs && geode == flowState.geode &&
                    oreRobots == flowState.oreRobots && clayRobots == flowState.clayRobots && obsRobots == flowState.obsRobots && geodeRobots == flowState.geodeRobots;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, ore, clay, obs, geode,
                    oreRobots, clayRobots, obsRobots, geodeRobots);
        }
    }

}

//    private static String serialize(int minute,
//                                    int ore, int clay, int obs, int geode,
//                                    int oreRobots, int clayRobots, int obsRobots, int geodeRobots) {
//        return String.valueOf(minute) + ',' +
//                ore + ',' +
//                clay + ',' +
//                obs + ',' +
//                //geode + ',' +
//                oreRobots + ',' +
//                clayRobots + ',' +
//                obsRobots + ',' +
//                geodeRobots + ',';
//    }


//    private static String printState(int minute, int ore, int clay, int obs, int geode,
//                                     int oreRobots, int clayRobots, int obsRobots, int geodeRobots) {
//        StringBuilder builder = new StringBuilder(50);
//        builder.append(" ".repeat(Math.max(0, (24 - minute))));
//        builder.append("min=").append(minute).append(" - ");
//        builder.append("ore=").append(ore).append(",");
//        builder.append("clay=").append(clay).append(",");
//        builder.append("obs=").append(obs).append(",");
//        builder.append("geode=").append(geode).append(",");
//        builder.append("oreRobots=").append(oreRobots).append(",");
//        builder.append("clayRobots=").append(clayRobots).append(",");
//        builder.append("obsRobots=").append(obsRobots).append(",");
//        builder.append("geodeRobots=").append(geodeRobots).append(",");
//        return builder.toString();
//    }
