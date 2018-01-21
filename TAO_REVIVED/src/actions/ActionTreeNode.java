// 
// Decompiled by Procyon v0.5.30
// 

package actions;

import java.util.ArrayList;

public class ActionTreeNode //file writing history
{
    private Action action;
    private String description;
    private ActionTreeNode parent;
    private ArrayList<ActionTreeNode> children;
    
    public ActionTreeNode() {
        this.children = new ArrayList<ActionTreeNode>();
        this.description = "";
    }
    
    private ActionTreeNode(final Action a) {
        this.action = a;
        this.description = "";
        this.children = new ArrayList<ActionTreeNode>();
    }
    
    public void setDescription(final String s) {
        this.description = s;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public ActionTreeNode getParent() {
        return this.parent;
    }
    
    public int numChildren() {
        return this.children.size();
    }
    
    public ActionTreeNode getChild(final int i) {
        return this.children.get(i);
    }
    
    public boolean isRoot() {
        return this.parent == null;
    }
    
    public ActionTreeNode getRoot() {
        ActionTreeNode temp;
        for (temp = this; !temp.isRoot(); temp = temp.getParent()) {}
        return temp;
    }
    
    public ActionTreeNode Add(final Action a) {
        final ActionTreeNode child = new ActionTreeNode(a);
        child.parent = this;
        this.children.add(child);
        return child;
    }
    
    public int getDepth() {
        ActionTreeNode temp;
        int depth;
        for (temp = this, depth = 0; !temp.isRoot(); temp = temp.getParent(), ++depth) {}
        return depth;
    }
    
    public int getVariationDepth() {
        ActionTreeNode temp;
        for (temp = this; !temp.isRoot() && temp.getParent().getChild(0) == temp; temp = temp.getParent()) {}
        return this.getDepth() - temp.getDepth();
    }
    
    public int getVariationLength() {
        ActionTreeNode temp;
        for (temp = this; temp.numChildren() != 0; temp = temp.getChild(0)) {}
        final int depthOfEnd = temp.getDepth();
        for (temp = this; !temp.isRoot() && temp.getParent().getChild(0) == temp; temp = temp.getParent()) {}
        final int depthOfStart = temp.getDepth();
        return depthOfEnd - depthOfStart;
    }
    
    public ActionTreeNode getLastNode() {
        ActionTreeNode temp;
        for (temp = this; temp.numChildren() != 0; temp = temp.getChild(0)) {}
        return temp;
    }
    
    public ActionTreeNode getNextIntersection() {
        ActionTreeNode temp;
        for (temp = this; temp.numChildren() == 1; temp = temp.getChild(0)) {}
        return temp;
    }
    
    public ActionTreeNode getPrevIntersection() {
        ActionTreeNode temp;
        for (temp = this; temp.numChildren() < 2 && !temp.isRoot(); temp = temp.getParent()) {}
        return temp;
    }
    
    public int[] getPath() {
        final int[] a = new int[this.getDepth()];
        ActionTreeNode temp = this;
        for (int i = a.length - 1; i >= 0; --i) {
            for (int j = 0; j < temp.getParent().numChildren(); ++j) {
                if (temp == temp.getParent().getChild(j)) {
                    a[i] = j;
                    break;
                }
            }
            temp = temp.getParent();
        }
        return a;
    }
    
    public String toString() {
        String output = "";
        if (!this.isRoot()) {
            output = String.valueOf(output) + this.action.toString();
            if (this.description != null && this.description.length() > 0) {
                String temp = this.description;
                temp = temp.replace("\\", "\\\\");
                temp = temp.replace("\"", "\\\"");
                temp = temp.replace("\n", "\\n");
                output = String.valueOf(output) + "\"" + temp + "\"";
            }
        }
        final int n = this.numChildren();
        if (n == 0) {
            return output;
        }
        if (n == 1) {
            return String.valueOf(output) + this.getChild(0).toString();
        }
        output = String.valueOf(output) + "(" + this.getChild(1).toString();
        for (int i = 2; i < n; ++i) {
            output = String.valueOf(output) + "," + this.getChild(i).toString();
        }
        output = String.valueOf(output) + ")" + this.getChild(0).toString();
        return output;
    }
    
    public ActionTreeNode Remove() {
        if (this != this.getLastNode()) {
            throw new IllegalStateException("Can't remove from the middle of the tree");
        }
        this.parent.children.remove(this);
        return this.parent;
    }
}
