package ws.bmocanu.aoc.ed2022;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day07NoSpaceLeftOnDevice extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day07"));
        Node root = new Node();
        Node currentDir = root;
        allNodes.add(root);

        for (String line : input) {
            if (line.equals("$ cd ..")) {
                currentDir = currentDir.parent;
            } else if (line.startsWith("$ cd ")) {
                currentDir = getChild(currentDir, line.split(" ")[2]);
                currentDir.isDir = true;
            } else if (Character.isDigit(line.charAt(0))) {
                String[] comp = line.split(" ");
                getChild(currentDir, comp[1]).size = Integer.parseInt(comp[0]);
            }
        }
        root.calculateTotalSize();

        Log.part1(allNodes.stream()
                      .mapToInt(node -> (node.isDir && node.totalSize <= 100000 ? node.totalSize : 0))
                      .sum());

        int spaceToFree = root.totalSize - 40000000;
        Log.part2(allNodes.stream()
                      .mapToInt(node -> (node.isDir && node.totalSize > spaceToFree ? node.totalSize : Integer.MAX_VALUE))
                      .min().orElseThrow());
    }

    private static Node getChild(Node cNode, String name) {
        for (Node child : cNode.children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        Node newChild = new Node();
        newChild.name = name;
        newChild.parent = cNode;
        cNode.children.add(newChild);
        allNodes.add(newChild);
        return newChild;
    }

    // ----------------------------------------------------------------------------------------------------

    private static final List<Node> allNodes = new LinkedList<>();

    private static class Node {

        public Node parent;
        public List<Node> children = new ArrayList<>();
        public boolean isDir;

        public String name;
        public int size;
        public int totalSize;

        public int calculateTotalSize() {
            totalSize = size;
            for (Node child : children) {
                totalSize += child.calculateTotalSize();
            }
            return totalSize;
        }

    }

}
