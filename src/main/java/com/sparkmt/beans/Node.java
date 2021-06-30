package com.sparkmt.beans;

import com.sparkmt.forms.DirectoryTreePopupMenu;

import javax.swing.*;
import java.awt.*;

public interface Node {
    Component getTreeCellRendererComponent(JTree tree, Node node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus);

    void populateDirectoryTreePopupMenu(DirectoryTreePopupMenu directoryTreePopupMenu);
}
