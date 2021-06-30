package com.sparkmt.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBPopupMenu;
import com.sparkmt.beans.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class DirectoryTreePopupMenu extends JBPopupMenu {
    private final JTree directoryTree;
    private final DefaultMutableTreeNode selectedNode;
    private final Node userObject;
    private final Project project;

    DirectoryTreePopupMenu(JTree directoryTree, DefaultMutableTreeNode selectedNode, Node userObject, Project project) {
        this.directoryTree = directoryTree;
        this.selectedNode = selectedNode;
        this.userObject = userObject;
        this.project = project;
        userObject.populateDirectoryTreePopupMenu(this);
    }

    public JTree getDirectoryTree() {
        return directoryTree;
    }

    public DefaultMutableTreeNode getSelectedNode() {
        return selectedNode;
    }

    public Node getUserObject() {
        return userObject;
    }

    public Project getProject() {
        return project;
    }
}
