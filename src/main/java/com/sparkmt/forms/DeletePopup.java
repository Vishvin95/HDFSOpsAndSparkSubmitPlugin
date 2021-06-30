package com.sparkmt.forms;

import com.sparkmt.beans.FileType;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.beans.Node;
import com.sparkmt.utils.HDFSUtils;
import com.sparkmt.utils.ProjectUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;

public class DeletePopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelMessageText;
    private HDFSConfiguration hdfsConfiguration;
    private JTree directoryTree;
    private Node userObject;
    private DefaultMutableTreeNode selectedNode;
    private String projectPath;

    public DeletePopup() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if(userObject instanceof HDFSFile) {
            HDFSFile hdfsFile = (HDFSFile) userObject;
            HDFSUtils.delete(hdfsConfiguration, hdfsFile.getPathSuffix(), hdfsFile.getType() == FileType.DIRECTORY);
        }
        else {
            ProjectUtils.deleteFile(projectPath + "/hdfs-configs/" + hdfsConfiguration.getConfigurationName() + ".cfg");
        }
        selectedNode.removeFromParent();
        FormUtils.refreshTreeModel(directoryTree);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setMessage(String message) {
        this.labelMessageText.setText(message);
    }

    public void setHdfsConfiguration(HDFSConfiguration hdfsConfiguration) {
        this.hdfsConfiguration = hdfsConfiguration;
    }

    public void setDirectoryTree(JTree directoryTree) {
        this.directoryTree = directoryTree;
    }

    public void setUserObject(Node userObject) {
        this.userObject = userObject;
    }

    public void setSelectedNode(DefaultMutableTreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
