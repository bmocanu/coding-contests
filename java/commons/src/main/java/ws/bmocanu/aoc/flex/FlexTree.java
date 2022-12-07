package ws.bmocanu.aoc.flex;

import java.util.LinkedList;
import java.util.List;

public class FlexTree {

    private final List<TreeNode> nodesList = new LinkedList<>();
    private TreeNode root;

    // ----------------------------------------------------------------------------------------------------

    public TreeNode addRoot(String name) {
        root = new TreeNode(this);
        root.name = name;
        internalRegisterNode(root);
        return root;
    }

    public TreeNode root() {
        return root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public List<TreeNode> allNodes() {
        return nodesList;
    }

    public void visitNodes(TreeNodeVisitor visitor) {
        root.internalVisitRecursively(visitor);
    }

    // ----------------------------------------------------------------------------------------------------

    protected void internalRegisterNode(TreeNode node) {
        nodesList.add(node);
    }

}
