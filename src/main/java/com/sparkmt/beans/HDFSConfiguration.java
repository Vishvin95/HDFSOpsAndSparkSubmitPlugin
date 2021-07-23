package com.sparkmt.beans;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBMenuItem;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.forms.DeletePopup;
import com.sparkmt.forms.DirectoryTreePopupMenu;
import com.sparkmt.forms.NewFolderPopup;
import com.sparkmt.forms.UploadFilePopup;
import com.sparkmt.constants.Icons;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Objects;

public class HDFSConfiguration implements Serializable, Node {
    private String configurationName;
    private String serverName;
    private String serverPort;
    private String hdfsLocation;
    private String mtToken;

    public HDFSConfiguration() {

    }

    public HDFSConfiguration(HDFSConfiguration hdfsConfiguration) {
        this.configurationName = hdfsConfiguration.configurationName;
        this.serverName = hdfsConfiguration.serverName;
        this.serverPort = hdfsConfiguration.serverPort;
        this.hdfsLocation = hdfsConfiguration.hdfsLocation;
        this.mtToken = hdfsConfiguration.mtToken;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getHdfsLocation() {
        return hdfsLocation;
    }

    public void setHdfsLocation(String hdfsLocation) {
        this.hdfsLocation = hdfsLocation;
    }

    public String getMtToken() {
        return mtToken;
    }

    public void setMtToken(String mtToken) {
        this.mtToken = mtToken;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public String toString() {
        return "HDFSConfiguration{" +
                "configurationName='" + configurationName + '\'' +
                ", serverName='" + serverName + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", hdfsLocation='" + hdfsLocation + '\'' +
                ", mtToken='" + mtToken + '\'' +
                '}';
    }

    @Override
    public void populateDirectoryTreePopupMenu(DirectoryTreePopupMenu directoryTreePopupMenu) {
        JBMenuItem mkdir = new JBMenuItem(StringConstants.MENU_OPTION_NEW_FOLDER);
        JBMenuItem upload = new JBMenuItem(StringConstants.MENU_OPTION_UPLOAD_FILE);
        JBMenuItem deleteHDFSConfiguration = new JBMenuItem(StringConstants.MENU_OPTION_DELETE_CONFIG);

        mkdir.addActionListener(e -> mkdirEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getProject()));
        upload.addActionListener(e -> uploadEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getProject()));
        deleteHDFSConfiguration.addActionListener(e -> deleteEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getProject()));

        directoryTreePopupMenu.add(mkdir);
        directoryTreePopupMenu.add(new JSeparator());
        directoryTreePopupMenu.add(upload);
        directoryTreePopupMenu.add(new JSeparator());
        directoryTreePopupMenu.add(deleteHDFSConfiguration);
    }

    private void deleteEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Project project) {
        HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) selectedNode.getUserObject();

        DeletePopup deletePopup = new DeletePopup();
        deletePopup.setHdfsConfiguration(hdfsConfiguration);
        deletePopup.setSelectedNode(selectedNode);
        deletePopup.setDirectoryTree(directoryTree);
        deletePopup.setProject(project);
        deletePopup.setMessage(StringConstants.CONFIRM_DELETE_CONFIG_MSG + " " + hdfsConfiguration.getConfigurationName() + "::" + hdfsConfiguration.getHdfsLocation());
        deletePopup.setTitle(StringConstants.MENU_OPTION_DELETE_CONFIG);
        deletePopup.setLocationByPlatform(true);
        deletePopup.setLocation(300, 300);
        deletePopup.pack();
        deletePopup.setVisible(true);
    }

    private void mkdirEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Project project) {
        HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) selectedNode.getUserObject();

        NewFolderPopup newFolderPopup = new NewFolderPopup();
        newFolderPopup.setHdfsConfiguration(hdfsConfiguration);
        newFolderPopup.setSelectedNode(selectedNode);
        newFolderPopup.setDirectoryTree(directoryTree);
        newFolderPopup.setProject(project);
        newFolderPopup.setTitle(StringConstants.MENU_OPTION_NEW_FOLDER);
        newFolderPopup.setLocationByPlatform(true);
        newFolderPopup.setLocation(300, 300);
        newFolderPopup.pack();
        newFolderPopup.setVisible(true);
    }

    private void uploadEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Project project) {
        HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) selectedNode.getUserObject();

        UploadFilePopup uploadFilePopup = new UploadFilePopup();
        uploadFilePopup.setHdfsConfiguration(hdfsConfiguration);
        uploadFilePopup.setSelectedNode(selectedNode);
        uploadFilePopup.setDirectoryTree(directoryTree);
        uploadFilePopup.setProject(project);
        uploadFilePopup.setTitle(StringConstants.MENU_OPTION_UPLOAD_FILE);
        uploadFilePopup.setLocationByPlatform(true);
        uploadFilePopup.setLocation(300, 300);
        uploadFilePopup.pack();
        uploadFilePopup.setVisible(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Node node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) node;
        JLabel label = new JLabel();
        label.setIcon(Icons.hdfs);
        label.setText(hdfsConfiguration.getConfigurationName() + " :: " + hdfsConfiguration.getHdfsLocation());
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HDFSConfiguration that = (HDFSConfiguration) o;
        return configurationName.equals(that.configurationName) && serverName.equals(that.serverName) && serverPort.equals(that.serverPort) && hdfsLocation.equals(that.hdfsLocation) && mtToken.equals(that.mtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurationName, serverName, serverPort, hdfsLocation, mtToken);
    }
}