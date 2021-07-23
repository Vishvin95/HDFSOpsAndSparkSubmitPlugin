package com.sparkmt.beans;

import com.intellij.openapi.ui.JBMenuItem;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.forms.DirectoryTreePopupMenu;
import com.sparkmt.constants.Icons;

import javax.swing.*;
import java.awt.*;

public class HeaderNode implements Node{
    private static HeaderNode headerNode;
    private final String value;

    private HeaderNode(){
        this.value = StringConstants.HDFS_HEADER_NODE_TEXT;
    }

    public static HeaderNode getInstance(){
        return headerNode==null? headerNode = new HeaderNode():headerNode;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void populateDirectoryTreePopupMenu(DirectoryTreePopupMenu directoryTreePopupMenu) {
        JBMenuItem addNewHDFSConfiguration = new JBMenuItem(StringConstants.HDFS_CONFIG_DIALOG_TITLE);
        directoryTreePopupMenu.add(addNewHDFSConfiguration);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Node node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        HeaderNode header = (HeaderNode) node;
        JLabel label = new JLabel();
        label.setIcon(Icons.hdfs);
        label.setText(header.getValue());
        return label;
    }
}
