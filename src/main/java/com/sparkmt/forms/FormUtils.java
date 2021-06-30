package com.sparkmt.forms;

import com.sparkmt.utils.BeanDeserializer;
import com.sparkmt.beans.HDFSConfiguration;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;

public class FormUtils {
    static HDFSConfiguration setHDFSLocationForViewing(String projectDirectory, String filename, JLabel labelHDFSLocationValue)
    {
        try {
            BeanDeserializer beanDeserializer = new BeanDeserializer(projectDirectory + "/hdfs-configs/" + filename);
            HDFSConfiguration currentHdfsConfiguration = (HDFSConfiguration) beanDeserializer.getObject();
            labelHDFSLocationValue.setText(currentHdfsConfiguration.getHdfsLocation());
            return currentHdfsConfiguration;
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return new HDFSConfiguration();
        }
    }

    static void refreshTreeModel(JTree directoryTree){
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) directoryTree.getModel();
        defaultTreeModel.reload();
    }
}
