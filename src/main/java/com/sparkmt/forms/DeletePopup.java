package com.sparkmt.forms;

import com.intellij.openapi.project.Project;
import com.sparkmt.beans.FileType;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.beans.Node;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.utils.HDFSUtils;
import com.sparkmt.utils.ProjectUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DeletePopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelMessageText;
    private HDFSConfiguration hdfsConfiguration;
    private JTree directoryTree;
    private Node userObject;
    private DefaultMutableTreeNode selectedNode;
    private Project project;

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
        if (userObject instanceof HDFSFile) {
            HDFSFile hdfsFile = (HDFSFile) userObject;
            HDFSUtils.delete(hdfsConfiguration, hdfsFile.getPathSuffix(), hdfsFile.getType() == FileType.DIRECTORY);
        } else {
            ProjectUtils.deleteFile(project.getBasePath() + "/hdfs-configs/" + hdfsConfiguration.getConfigurationName() + ".cfg");
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

    public void setProject(Project project) {
        this.project = project;
    }
}
