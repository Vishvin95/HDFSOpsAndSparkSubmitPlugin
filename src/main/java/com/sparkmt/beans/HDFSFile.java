package com.sparkmt.beans;

import com.intellij.openapi.ui.JBMenuItem;
import com.sparkmt.forms.*;
import icons.PluginIcons;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HDFSFile implements Node {
    private String pathSuffix;
    private FileType type;
    private int length;
    private String owner;
    private String group;
    private int permission;
    private long accessTime;
    private long modificationTime;
    private long blockSize;
    private int replication;


    public HDFSFile() {
    }

    public String getPathSuffix() {
        return pathSuffix;
    }

    public void setPathSuffix(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public long getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(long blockSize) {
        this.blockSize = blockSize;
    }

    public int getReplication() {
        return replication;
    }

    public void setReplication(int replication) {
        this.replication = replication;
    }


    @Override
    public Component getTreeCellRendererComponent(JTree tree, Node node, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = new JLabel();
        HDFSFile hdfsFile = (HDFSFile) node;
        if (hdfsFile.getType() == FileType.DIRECTORY)
            label.setIcon(PluginIcons.folder);
        else
            label.setIcon(PluginIcons.file);
        label.setText(hdfsFile.getPathSuffix());
        return label;
    }

    @Override
    public void populateDirectoryTreePopupMenu(DirectoryTreePopupMenu directoryTreePopupMenu) {
        HDFSFile hdfsFile = (HDFSFile) directoryTreePopupMenu.getUserObject();
        if (hdfsFile.getType() == FileType.DIRECTORY) {
            JBMenuItem mkdir = new JBMenuItem("New Folder");
            JBMenuItem upload = new JBMenuItem("Upload file");

            mkdir.addActionListener(e -> mkdirEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                    directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getUserObject()));
            upload.addActionListener(e -> uploadEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                    directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getUserObject()));

            directoryTreePopupMenu.add(mkdir);
            directoryTreePopupMenu.add(new JSeparator());
            directoryTreePopupMenu.add(upload);
            directoryTreePopupMenu.add(new JSeparator());
        }
        JBMenuItem rename = new JBMenuItem("Rename");
        JBMenuItem delete = new JBMenuItem("Delete");

        rename.addActionListener(e -> renameEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getUserObject()));
        delete.addActionListener(e -> deleteEventHandler(e, directoryTreePopupMenu.getDirectoryTree(),
                directoryTreePopupMenu.getSelectedNode(), directoryTreePopupMenu.getUserObject()));

        directoryTreePopupMenu.add(rename);
        directoryTreePopupMenu.add(new JSeparator());
        directoryTreePopupMenu.add(delete);
    }

    private void mkdirEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Node userObject) {
        HDFSFile hdfsFile = (HDFSFile) userObject;

        HDFSConfiguration finalHdfsConfiguration = getRootHDFSConfiguration(selectedNode);
        finalHdfsConfiguration.setHdfsLocation(finalHdfsConfiguration.getHdfsLocation() + "/" + hdfsFile.getPathSuffix());

        NewFolderPopup newFolderPopup = new NewFolderPopup();
        newFolderPopup.setHdfsConfiguration(finalHdfsConfiguration);
        newFolderPopup.setSelectedNode(selectedNode);
        newFolderPopup.setDirectoryTree(directoryTree);
        newFolderPopup.setTitle("Create HDFS Directory");
        newFolderPopup.setLocationByPlatform(true);
        newFolderPopup.setLocation(300, 300);
        newFolderPopup.pack();
        newFolderPopup.setVisible(true);
    }

    private void uploadEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Node userObject) {
        HDFSFile hdfsFile = (HDFSFile) userObject;

        HDFSConfiguration finalHdfsConfiguration = getRootHDFSConfiguration(selectedNode);
        finalHdfsConfiguration.setHdfsLocation(finalHdfsConfiguration.getHdfsLocation() + "/" + hdfsFile.getPathSuffix());

        UploadFilePopup uploadFilePopup = new UploadFilePopup();
        uploadFilePopup.setHdfsConfiguration(finalHdfsConfiguration);
        uploadFilePopup.setSelectedNode(selectedNode);
        uploadFilePopup.setDirectoryTree(directoryTree);
        uploadFilePopup.setTitle("Upload File To HDFS");
        uploadFilePopup.setLocationByPlatform(true);
        uploadFilePopup.setLocation(300, 300);
        uploadFilePopup.pack();
        uploadFilePopup.setVisible(true);
    }

    private void deleteEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Node userObject) {
        HDFSFile hdfsFile = (HDFSFile) userObject;

        // Build a confirmation dialog
        DeletePopup deletePopup = new DeletePopup();
        deletePopup.setHdfsConfiguration(getRootHDFSConfiguration(selectedNode));
        deletePopup.setDirectoryTree(directoryTree);
        deletePopup.setUserObject(userObject);
        deletePopup.setSelectedNode(selectedNode);

        if (hdfsFile.getType() == FileType.FILE)
            deletePopup.setMessage("Do you want to delete the file? " + hdfsFile.getPathSuffix());
        else
            deletePopup.setMessage("Folder will be deleted recursively. Do you want to delete the folder? " + hdfsFile.getPathSuffix());

        deletePopup.setTitle("Delete HDFS File");
        deletePopup.setLocationByPlatform(true);
        deletePopup.setLocation(300, 300);
        deletePopup.pack();
        deletePopup.setVisible(true);
    }

    private void renameEventHandler(ActionEvent e, JTree directoryTree, DefaultMutableTreeNode selectedNode, Node userObject) {
        HDFSFile hdfsFile = (HDFSFile) userObject;

        RenameHDFSFilePopup renameHDFSFilePopup = new RenameHDFSFilePopup();
        renameHDFSFilePopup.setHdfsConfiguration(getRootHDFSConfiguration(selectedNode));
        renameHDFSFilePopup.setOldFileName(hdfsFile.getPathSuffix());
        renameHDFSFilePopup.setDirectoryTree(directoryTree);
        renameHDFSFilePopup.setUserObject(hdfsFile);
        renameHDFSFilePopup.setTitle("Rename HDFS File");
        renameHDFSFilePopup.setLocationByPlatform(true);
        renameHDFSFilePopup.setLocation(300, 300);
        renameHDFSFilePopup.pack();
        renameHDFSFilePopup.setVisible(true);
    }

    // Does leaf to root traversal
    private HDFSConfiguration getRootHDFSConfiguration(DefaultMutableTreeNode selectedNode){
        StringBuilder parentPath = new StringBuilder();
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
        while(!(parent.getUserObject() instanceof HDFSConfiguration)){
            HDFSFile currentHDFSFile = (HDFSFile) parent.getUserObject();
            parentPath.append(new StringBuilder(currentHDFSFile.getPathSuffix()).reverse()).append("/");
            parent = (DefaultMutableTreeNode) parent.getParent();
        }
        HDFSConfiguration hdfsConfiguration = (HDFSConfiguration) parent.getUserObject();
        HDFSConfiguration parentPathHDFSConfiguration = new HDFSConfiguration(hdfsConfiguration);
        parentPathHDFSConfiguration.setHdfsLocation(parentPathHDFSConfiguration.getHdfsLocation() + parentPath.reverse().toString());
        return parentPathHDFSConfiguration;
    }

    @Override
    public String toString() {
        return "HDFSFileStatus{" +
                "pathSuffix='" + pathSuffix + '\'' +
                ", type=" + type +
                ", length=" + length +
                ", owner='" + owner + '\'' +
                ", group='" + group + '\'' +
                ", permission=" + permission +
                ", accessTime=" + accessTime +
                ", modificationTime=" + modificationTime +
                ", blockSize=" + blockSize +
                ", replication=" + replication +
                '}';
    }
}
