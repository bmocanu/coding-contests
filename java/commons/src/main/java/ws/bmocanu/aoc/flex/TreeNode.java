package ws.bmocanu.aoc.flex;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {

    private FlexTree ownerTree;
    public String name;
    public boolean marked;
    public int size;

    public TreeNode parent;
    public List<TreeNode> children = new LinkedList<>();

    // ----------------------------------------------------------------------------------------------------

    public TreeNode(FlexTree ownerTree) {
        this.ownerTree = ownerTree;
    }

    // ----------------------------------------------------------------------------------------------------

    public TreeNode mark() {
        this.marked = true;
        return this;
    }

    public TreeNode getChild(String name) {
        for (TreeNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return addChild(name);
    }

    public TreeNode addChild(String name) {
        TreeNode newChild = new TreeNode(ownerTree);
        newChild.parent = this;
        newChild.name = name;
        children.add(newChild);
        ownerTree.internalRegisterNode(newChild);
        return newChild;
    }

    // ----------------------------------------------------------------------------------------------------

    protected void internalVisitRecursively(TreeNodeVisitor visitor) {
        for (TreeNode child : children) {
            child.internalVisitRecursively(visitor);
        }
        visitor.visit(this);
    }

}
