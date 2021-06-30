package com.sparkmt.forms;

import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;
import com.sparkmt.beans.Node;
import com.sparkmt.utils.HDFSUtils;

import javax.swing.*;
import java.awt.event.*;

public class RenameHDFSFilePopup extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldRenameFileTo;
    private HDFSConfiguration hdfsConfiguration;
    private String oldFileName;
    private JTree directoryTree;
    private Node userObject;

    public RenameHDFSFilePopup() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        textFieldRenameFileTo.setText(oldFileName);
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
        HDFSUtils.rename(hdfsConfiguration, oldFileName, textFieldRenameFileTo.getText());
        HDFSFile hdfsFile = (HDFSFile) userObject;
        hdfsFile.setPathSuffix(textFieldRenameFileTo.getText());
        FormUtils.refreshTreeModel(directoryTree);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setHdfsConfiguration(HDFSConfiguration hdfsConfiguration) {
        this.hdfsConfiguration = hdfsConfiguration;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public void setDirectoryTree(JTree directoryTree) {
        this.directoryTree = directoryTree;
    }

    public void setUserObject(Node userObject) {
        this.userObject = userObject;
    }
}
