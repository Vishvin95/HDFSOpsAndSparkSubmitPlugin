package com.sparkmt.forms;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBPopupMenu;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.treeStructure.Tree;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.beans.HeaderNode;
import com.sparkmt.beans.Node;
import com.sparkmt.utils.BeanDeserializer;
import com.sparkmt.utils.HDFSUtils;
import com.sparkmt.utils.ProjectUtils;
import icons.PluginIcons;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HDFSToolWindow {

    private JPanel hdfsToolWindowContent;
    private JTree tree1;
    private JButton buttonRefresh;
    private JPanel panelWindowMenu;
    private JBPopupMenu treePopupMenu;

    private final Project project;

    public HDFSToolWindow(Project project, ToolWindow toolWindow) {
        this.project = project;
    }

    public JPanel getContent() {
        return hdfsToolWindowContent;
    }

    public ArrayList<Node> getAllHDFSConfigurations(String projectBasePath) {
        ArrayList<Node> hdfsConfigurations = new ArrayList<>();
        String[] hdfsConfigFilenames = ProjectUtils.getFilenamesInDirectory(projectBasePath + "/hdfs-configs");
        if (hdfsConfigFilenames !=null && hdfsConfigFilenames.length != 0) {
            Arrays.stream(hdfsConfigFilenames).forEach(hdfsConfigFile -> {
                try {
                    BeanDeserializer beanDeserializer = new BeanDeserializer(projectBasePath + "/hdfs-configs/" + hdfsConfigFile);
                    HDFSConfiguration config = (HDFSConfiguration) beanDeserializer.getObject();
                    hdfsConfigurations.add(config);
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            });
        }
        return hdfsConfigurations;
    }

    private void createUIComponents() {
        buttonRefresh = new JButton();
        buttonRefresh.setIcon(PluginIcons.refresh);
        buttonRefresh.addActionListener(e -> refreshDirectoryTree());

        generateDirectoryTree();
    }

    private void refreshDirectoryTree() {
        ArrayList<Node> existingHDFSConfigurations = getAllHDFSConfigurations(project.getBasePath());
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree1.getModel().getRoot();
        int currentCountOfHDFSConfigurations = root.getChildCount();
        for (int i = 0; i < currentCountOfHDFSConfigurations; i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) child.getUserObject();
            if (existingHDFSConfigurations.contains(hdfsConfiguration)) {
                existingHDFSConfigurations.remove(hdfsConfiguration);
            }
        }

        existingHDFSConfigurations.forEach(hdfsConfiguration -> {
            addChildHDFSConfigurationNodes(root, (HDFSConfiguration) hdfsConfiguration);
        });

        FormUtils.refreshTreeModel(tree1);
    }

    private void addChildHDFSConfigurationNodes(DefaultMutableTreeNode root, HDFSConfiguration hdfsConfiguration) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(hdfsConfiguration);
        ArrayList<HDFSFile> fileStatuses = HDFSUtils.listFiles(hdfsConfiguration, "");
        if (fileStatuses != null && !fileStatuses.isEmpty()) {
            fileStatuses.forEach(hdfsFile -> {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(hdfsFile);
                node.add(childNode);
            });
        }
        root.add(node);
    }

    private void generateDirectoryTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(HeaderNode.getInstance());
        tree1 = new Tree(rootNode);
        tree1.setCellRenderer(new DirectoryTreeCellRender());

        getAllHDFSConfigurations(project.getBasePath()).forEach(hdfsConfiguration -> {
            addChildHDFSConfigurationNodes(rootNode, (HDFSConfiguration) hdfsConfiguration);
        });

        tree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                TreePath selectedPath = tree1.getPathForLocation(e.getX(), e.getY());
                tree1.setSelectionPath(selectedPath);
                if (selectedPath != null) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    if (selectedNode != null) {
                        Node selectedUserObject = (Node) selectedNode.getUserObject();
                        if (e.isPopupTrigger()) {
                            treePopupMenu = new DirectoryTreePopupMenu(tree1, selectedNode, selectedUserObject, project);
                            treePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
            }
        });

        FormUtils.refreshTreeModel(tree1);
    }
}
