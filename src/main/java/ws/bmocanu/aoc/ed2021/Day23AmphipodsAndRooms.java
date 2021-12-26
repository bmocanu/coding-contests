package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.FlexStruct;
import ws.bmocanu.aoc.flex.Point;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SList;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.*;

public class Day23AmphipodsAndRooms extends SolutionBase {

    static char SPACE = '.';
    static int ROOM_HEIGHT = 4;
    static Map<Character, Integer> ENERGY_MAP = new HashMap<>() {{
        put('A', 1);
        put('B', 10);
        put('C', 100);
        put('D', 1000);
    }};
    static Map<Character, Integer> ROOMX_MAP = new HashMap<>() {{
        put('A', 3);
        put('B', 5);
        put('C', 7);
        put('D', 9);
    }};
    static int[] ROOMX_VALUES = {3, 5, 7, 9};

    static int WIDTH;
    static int HEIGHT;
    static Map<String, Node> nodesMap = new HashMap<>();
    static SList<Node> nodesToIterate = new SList<>();
    static Node startNode;
    static Node finishedNode;

    public static void main(String[] args) {

        FlexStruct flex = FlexStruct.fromFile(filePath("day23"));
        WIDTH = flex.width();
        HEIGHT = flex.height();

        startNode = new Node(flex.charactersToSha256(), flex);
        nodesToIterate.add(startNode);
        nodesMap.put(startNode.id, startNode);

        System.out.println("Generating all state nodes...");
        generateAllStateNodes();
        System.out.println("Calculating the best movement path...");
        calculateBestMovementPath();

        Log.part1(0); // part 1 was done by hand
        Log.part2(finishedNode.cost);

        //reversePrintTheBestMovementPath(finishedNode, 0);
    }

    static void generateAllStateNodes() {
        while (!nodesToIterate.isEmpty()) {
            Node currentNode = nodesToIterate.popFirst();
            FlexStruct currentFlex = currentNode.flex;
            for (char chr = 'A'; chr <= 'D'; chr++) {
                for (Point chrP : currentFlex.allPointsOfChar(chr)) {
                    if (chrP.y >= 2) {
                        // amp is in a room
                        if (chrP.x != ROOMX_MAP.get(chrP.chr) || ampIsInBadCompany(currentFlex, chrP)) {
                            // amp needs to get out of the room
                            int y = chrP.y;
                            int yMoves = 0;
                            boolean moveBlocked = false;
                            while (y > 1) {
                                y--;
                                if (currentFlex.point(chrP.x, y).chr != SPACE) {
                                    moveBlocked = true;
                                    break;
                                } else {
                                    yMoves++;
                                }
                            }
                            if (!moveBlocked) {
                                // amp is now in the hallway, no node to create yet
                                // try to move to the left
                                analyzeAmpMovementInTheHallway(currentNode, currentFlex, chrP, -1, yMoves);
                                // try to move to the right
                                analyzeAmpMovementInTheHallway(currentNode, currentFlex, chrP, 1, yMoves);
                            }
                        }
                    } else {
                        // amp is in the hallway, might be able to move in the room
                        // at this point the move in the room is opportunistic, as an amphipod should
                        // move to its room as soon as it feasible to do it
                        analyzeAmpMovementToARoom(currentNode, currentFlex, chrP);
                    }
                }
            }
        }
    }

    static void analyzeAmpMovementInTheHallway(Node currentNode, FlexStruct flex,
                                               Point chrP, int delta, int extraMoves) {
        int x = chrP.x;
        while (x > 0 && x < WIDTH) {
            x += delta;
            Point hallwayP = flex.point(x, 1);
            if (hallwayP.chr == SPACE) {
                if (!XUtils.intOneOf(x, ROOMX_VALUES)) { // cannot stay on top of a room
                    FlexStruct newFlex = flex.deepClone();
                    newFlex.point(chrP).chr = SPACE;
                    newFlex.point(x, 1).chr = chrP.chr;
                    considerAddingNewNode(currentNode, newFlex,
                            (extraMoves + Math.abs(chrP.x - x)) * ENERGY_MAP.get(chrP.chr));
                }
            } else {
                break;
            }
        }

    }

    static void analyzeAmpMovementToARoom(Node currentNode, FlexStruct flex, Point chrP) {
        int roomX = ROOMX_MAP.get(chrP.chr);
        // is the room free?
        for (int y = 2; y < 2 + ROOM_HEIGHT; y++) {
            char currentChr = flex.point(roomX, y).chr;
            if (currentChr != SPACE && currentChr != chrP.chr) {
                return;
            }
        }
        // calculate the depth in the room; an amp should go as low as possible in the room
        int roomY = 2; // room y = 2..ROOM_HEIGHT
        while (flex.point(roomX, roomY + 1).chr == SPACE) {
            roomY++;
        }
        // now check if the path to the room is free
        int ampX = chrP.x;
        int ampY = chrP.y;
        while (ampX > roomX) {
            ampX--;
            if (flex.point(ampX, ampY).chr != SPACE) {
                return;
            }
        }
        while (ampX < roomX) {
            ampX++;
            if (flex.point(ampX, ampY).chr != SPACE) {
                return;
            }
        }
        // ok so the path seems to be free
        FlexStruct newFlex = flex.deepClone();
        newFlex.point(chrP).chr = SPACE;
        newFlex.point(roomX, roomY).chr = chrP.chr;
        considerAddingNewNode(currentNode, newFlex,
                (Math.abs(chrP.x - roomX) + (roomY - chrP.y)) * ENERGY_MAP.get(chrP.chr));
    }

    static void considerAddingNewNode(Node parent, FlexStruct newFlex, int cost) {
        String newFlexId = newFlex.charactersToSha256();
        Node node = nodesMap.get(newFlexId);
        if (node == null) {
            node = new Node(newFlexId, newFlex);
            nodesMap.put(newFlexId, node);
            nodesToIterate.add(node);
            if (flexMatchesFinishedState(newFlex)) {
                finishedNode = node;
            }
        }
        Link newLink = new Link(node, cost);
        parent.links.add(newLink);
    }

    static boolean flexMatchesFinishedState(FlexStruct flex) {
        for (char chr = 'A'; chr <= 'D'; chr++) {
            int roomX = ROOMX_MAP.get(chr);
            for (int y = 2; y < 2 + ROOM_HEIGHT; y++) {
                if (flex.point(roomX, y).chr != chr) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean ampIsInBadCompany(FlexStruct flex, Point p) {
        for (int y = 2; y < 2 + ROOM_HEIGHT; y++) {
            Point otherP = flex.point(p.x, y);
            if (otherP.chr != SPACE && otherP.chr != p.chr) {
                return true;
            }
        }
        return false;
    }

    static void calculateBestMovementPath() {
        List<Node> nodesToProcess = new LinkedList<>();
        nodesToProcess.add(startNode);
        startNode.cost = 0;
        while (!finishedNode.visited) {
            int smallestCost = Integer.MAX_VALUE;
            Node currentNode = nodesToProcess.get(0);
            for (Node otherNode : nodesToProcess) {
                if (otherNode.cost < smallestCost) {
                    currentNode = otherNode;
                    smallestCost = otherNode.cost;
                }
            }

            for (Link link : currentNode.links) {
                Node linkedNode = link.otherNode;
                if (!linkedNode.visited) {
                    int tentativeCost = currentNode.cost + link.cost;
                    if (tentativeCost < linkedNode.cost) {
                        linkedNode.cost = tentativeCost;
                        linkedNode.backTrackParent = currentNode;
                        nodesToProcess.add(linkedNode);
                    }
                }
            }
            currentNode.visited = true;
            nodesToProcess.remove(currentNode);
        }
    }

    @SuppressWarnings("unused")
    static int reversePrintTheBestMovementPath(Node node, int depth) {
        int maxDepth = depth + 1;
        if (node.backTrackParent != null) {
            maxDepth = reversePrintTheBestMovementPath(node.backTrackParent, depth + 1);
        }
        System.out.println("------------------------- Move nr: " + (maxDepth - depth) + " / " + maxDepth);
        System.out.print(node.flex.charactersToString());
        return maxDepth;
    }

    // ----------------------------------------------------------------------------------------------------

    static class Node implements Comparable<Node> {
        String id;
        FlexStruct flex;
        Node backTrackParent; // the "parent" in the best movement path; used for reverse printing the best movements list
        List<Link> links = new LinkedList<>();
        int cost = Integer.MAX_VALUE;
        boolean visited;

        public Node(String id, FlexStruct flex) {
            this.id = id;
            this.flex = flex;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return id.equals(node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

    }

    static class Link {
        Node otherNode;
        int cost;

        public Link(Node otherNode, int cost) {
            this.otherNode = otherNode;
            this.cost = cost;
        }
    }

}
