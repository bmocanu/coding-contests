package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.flex.FlexTree;
import ws.bmocanu.aoc.flex.TreeNode;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day07NoSpaceLeftOnDevice extends SolutionBase {

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day07"));
        FlexTree tree = new FlexTree();
        TreeNode currentDir = tree.addRoot("/");
        currentDir.mark();

        for (String line : input) {
            if (line.equals("$ cd ..")) {
                currentDir = currentDir.parent;
            } else if (line.startsWith("$ cd ")) {
                currentDir = currentDir.addChild(line.split(" ")[2]);
                currentDir.mark();
            } else if (Character.isDigit(line.charAt(0))) {
                String[] comp = line.split(" ");
                currentDir.getChild(comp[1]).size = Integer.parseInt(comp[0]);
            }
        }
        tree.visitNodes(node -> {
            for (TreeNode child : node.children) {
                node.size += child.size;
            }
        });

        Log.part1(tree.allNodes().stream()
                .mapToInt(node -> (node.marked && node.size <= 100000 ? node.size : 0))
                .sum());

        int spaceToFree = tree.root().size - 40000000;
        Log.part2(tree.allNodes().stream()
                .mapToInt(node -> (node.marked && node.size > spaceToFree ? node.size : Integer.MAX_VALUE))
                .min().orElseThrow());
    }

}
