package com.sparkmt.forms;

import com.sparkmt.beans.FileType;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.utils.HDFSUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class UploadFilePopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldSelectedFile;
    private JButton buttonFileChooser;
    private JFileChooser fileChooser;

    private HDFSConfiguration hdfsConfiguration;
    private DefaultMutableTreeNode selectedNode;
    private JTree directoryTree;

    public UploadFilePopup() {
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

        buttonFileChooser.addActionListener(e -> {
            fileChooser = new JFileChooser();
            int approved = fileChooser.showOpenDialog(this);
            if (approved == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                textFieldSelectedFile.setText(file.getAbsolutePath());
            }
        });
    }

    private void onOK() {
        try {
            HDFSUtils.uploadFile(hdfsConfiguration, textFieldSelectedFile.getText());

            HDFSFile newFile = new HDFSFile();
            newFile.setPathSuffix(getFileName(textFieldSelectedFile.getText()));
            newFile.setType(FileType.FILE);
            DefaultMutableTreeNode newFileNode = new DefaultMutableTreeNode(newFile);
            selectedNode.add(newFileNode);
            FormUtils.refreshTreeModel(directoryTree);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

    private String getFileName(String absoluteFilePath){
        return absoluteFilePath.substring(1 + absoluteFilePath.lastIndexOf("\\"));
    }
}
