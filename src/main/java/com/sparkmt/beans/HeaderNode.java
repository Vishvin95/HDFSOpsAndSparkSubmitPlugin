package com.sparkmt.beans;

import com.intellij.openapi.ui.JBMenuItem;
import com.sparkmt.forms.DirectoryTreePopupMenu;
import icons.PluginIcons;

import javax.swing.*;
import java.awt.*;

public class HeaderNode implements Node{
    private static HeaderNode headerNode;
    private final String value;

    private HeaderNode(){
        this.value = "HDFS Locations";
    }

    public static HeaderNode getInstance(){
        return headerNode==null? headerNode = new HeaderNode():headerNode;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void populateDirectoryTreePopupMenu(DirectoryTreePopupMenu directoryTreePopupMenu) {
        JBMenuItem addNewHDFSConfiguration = new JBMenuItem("New HDFS Configuration");
//        addNewHDFSConfiguration.addActionListener(e -> newHDFSConfigEventHandler(e));
        directoryTreePopupMenu.add(addNewHDFSConfiguration);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Node node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        HeaderNode header = (HeaderNode) node;
        JLabel label = new JLabel();
        label.setIcon(PluginIcons.hdfs);
        label.setText(header.getValue());
        return label;
    }
}
