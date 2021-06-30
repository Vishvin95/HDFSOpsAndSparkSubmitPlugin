package com.sparkmt.forms;

import com.sparkmt.beans.FileType;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.utils.HDFSUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;

public class NewFolderPopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNewFolderName;

    private HDFSConfiguration hdfsConfiguration;
    private DefaultMutableTreeNode selectedNode;
    private JTree directoryTree;

    public NewFolderPopup() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // Do HDFS mkdir operation
        HDFSUtils.createDirectory(hdfsConfiguration, textFieldNewFolderName.getText());

        // Adjust the tree model
        HDFSFile newDirectory = new HDFSFile();
        newDirectory.setPathSuffix(textFieldNewFolderName.getText());
        newDirectory.setType(FileType.DIRECTORY);
        DefaultMutableTreeNode newFileNode = new DefaultMutableTreeNode(newDirectory);
        selectedNode.add(newFileNode);
        FormUtils.refreshTreeModel(directoryTree);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setHdfsConfiguration(HDFSConfiguration hdfsConfiguration) {
        this.hdfsConfiguration = hdfsConfiguration;
    }

    public void setDirectoryTree(JTree directoryTree) {
        this.directoryTree = directoryTree;
    }

    public void setSelectedNode(DefaultMutableTreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
