package com.sparkmt.forms;

import com.sparkmt.beans.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class DirectoryTreeCellRender implements TreeCellRenderer {
    DirectoryTreeCellRender(){

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Object object = ((DefaultMutableTreeNode) value).getUserObject();
        Node node = (Node) object;
        return node.getTreeCellRendererComponent(tree, node, selected, expanded, leaf, row, hasFocus);
    }
}
