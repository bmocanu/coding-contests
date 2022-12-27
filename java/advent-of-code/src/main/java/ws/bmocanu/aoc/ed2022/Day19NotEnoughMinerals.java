package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
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

        blueprintList.forEach(Blueprint::init);

        int part1Sum = blueprintList
                .stream()
                .mapToInt(blueprint -> {
                    int geodes = calculateGeodes(blueprint, 24);
                    System.out.println("Part1: Blueprint id=" + blueprint.id + ", geodes=" + geodes);
                    return geodes * blueprint.id;
                })
                .sum();
        Log.part1(part1Sum);

        int part2Product = 1;
        for (int index = 0; index < 3; index++) {
            Blueprint blueprint = blueprintList.get(index);
            int geodes = calculateGeodes(blueprint, 32);
            System.out.println("Part2: Blueprint id=" + blueprint.id + ", geodes=" + geodes);
            part2Product *= geodes;
        }

        Log.part2(part2Product);
    }

    // ----------------------------------------------------------------------------------------------------

    private static int calculateGeodes(Blueprint blueprint, int maxMinutes) {
        Set<FlowState> visitedStates = new HashSet<>();
        SList<FlowState> statesToProcess = new SList<>();
        FlowState startState = new FlowState(0, 0, 0, 0, 0,
                1, 0, 0, 0);
        statesToProcess.add(startState);
        visitedStates.add(startState);
        int maxGeode = 0;
        while (!statesToProcess.isEmpty()) {
            FlowState currentState = statesToProcess.popLast();
            if (currentState.depth < maxMinutes) {
                for (FlowState nextState : currentState.getNextStates(blueprint)) {
                    if (!visitedStates.contains(nextState) &&
                            nextState.getPotentialGeodeToProduce(maxMinutes) > maxGeode) {
                        visitedStates.add(currentState);
                        statesToProcess.add(nextState);
                    }
                }
            } else if (currentState.depth == maxMinutes) {
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
        int maxObsRobotsNeeded;
        int maxClayRobotsNeeded;
        int maxOreRobotsNeeded;

        public void init() {
            maxOreNeeded = XUtils.maxFromVarargs(oreRobotCostInOre, clayRobotCostInOre, obsidianRobotCostInOre, geodeRobotCostInOre).value;
            maxClayNeeded = obsidianRobotCostInClay;
            maxObsNeeded = geodeRobotCostInObsidian;
            maxObsRobotsNeeded = geodeRobotCostInObsidian;
            maxClayRobotsNeeded = obsidianRobotCostInClay;
            maxOreRobotsNeeded = XUtils.maxFromVarargs(oreRobotCostInOre, clayRobotCostInOre, obsidianRobotCostInOre, geodeRobotCostInOre).value;
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
            List<FlowState> result = new LinkedList<>();
            result.add(new FlowState(depth + 1, ore + oreRobots,
                    clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                    oreRobots, clayRobots, obsRobots, geodeRobots));
            // there is no limit in building geode robots. If we can build a new robot, we do it
            if (ore >= blueprint.geodeRobotCostInOre && obs >= blueprint.geodeRobotCostInObsidian) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.geodeRobotCostInOre,
                        clay + clayRobots, obs + obsRobots - blueprint.geodeRobotCostInObsidian, geode + geodeRobots,
                        oreRobots, clayRobots, obsRobots, geodeRobots + 1));
            }
            // don't build more obs robots than needed for new geode robots
            if (obsRobots < blueprint.maxObsRobotsNeeded &&
                    ore >= blueprint.obsidianRobotCostInOre && clay >= blueprint.obsidianRobotCostInClay) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.obsidianRobotCostInOre,
                        clay + clayRobots - blueprint.obsidianRobotCostInClay, obs + obsRobots, geode + geodeRobots,
                        oreRobots, clayRobots, obsRobots + 1, geodeRobots));
            }
            // don't build more clay robots than needed for new obs robots
            if (clayRobots < blueprint.maxClayRobotsNeeded && ore >= blueprint.clayRobotCostInOre) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.clayRobotCostInOre,
                        clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                        oreRobots, clayRobots + 1, obsRobots, geodeRobots));
            }
            if (oreRobots < blueprint.maxOreRobotsNeeded && ore >= blueprint.oreRobotCostInOre) {
                result.add(new FlowState(depth + 1, ore + oreRobots - blueprint.oreRobotCostInOre,
                        clay + clayRobots, obs + obsRobots, geode + geodeRobots,
                        oreRobots + 1, clayRobots, obsRobots, geodeRobots));
            }
            return result;
        }

        public int getPotentialGeodeToProduce(int maxMinutes) {
            int minutesLeft = maxMinutes - depth;
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
