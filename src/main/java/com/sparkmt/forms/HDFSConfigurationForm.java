package com.sparkmt.forms;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.Node;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.utils.BeanDeserializer;
import com.sparkmt.utils.BeanSerializer;
import com.sparkmt.utils.ProjectUtils;
import jnr.ffi.Struct;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class HDFSConfigurationForm extends DialogWrapper {
    private JPanel panelDeploymentSettingsPopup;
    private JLabel labelConfigurationName;
    private JTextField textFieldConfigurationName;
    private JLabel labelHDFSLocation;
    private JTextField textFieldHDFSLocation;
    private JLabel labelMTToken;
    private JTextArea textAreaMTToken;
    private JLabel labelServerName;
    private JTextField textFieldServerName;
    private JLabel labelServerPort;
    private JTextField textFieldServerPort;

    private final String projectBasePath;

    public HDFSConfigurationForm(String projectBasePath) {
        super(true);
        this.projectBasePath = projectBasePath;
        setTitle(StringConstants.HDFS_CONFIG_DIALOG_TITLE);
        setResizable(false);
        setSize(400, 550);
        setData(getExistingSettings());
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panelDeploymentSettingsPopup;
    }

    @Override
    protected void doOKAction() {
        HDFSConfiguration hdfsConfiguration = new HDFSConfiguration();
        getData(hdfsConfiguration);

        try {
            String newHdfsConfigFileName = hdfsConfiguration.getConfigurationName().trim().replaceAll("\\s", "_");
            if (!ProjectUtils.checkFileExists(projectBasePath + StringConstants.HDFS_CONFIG_PROJECT_SUB_PATH))
                ProjectUtils.createDirectory(projectBasePath + StringConstants.HDFS_CONFIG_PROJECT_SUB_PATH);
            BeanSerializer<Node> beanSerializer = new BeanSerializer<>(projectBasePath + StringConstants.HDFS_CONFIG_PROJECT_SUB_PATH + "/" + newHdfsConfigFileName + ".cfg");
            beanSerializer.writeObject(hdfsConfiguration);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        super.doOKAction();
    }

    @Override
    protected @Nullable ValidationInfo doValidate() {
        return super.doValidate();
    }

    private HDFSConfiguration getExistingSettings() {
        try {
            File lastFile = ProjectUtils.getLastFilenameInDirectory(projectBasePath + StringConstants.HDFS_CONFIG_PROJECT_SUB_PATH + "/");
            if (lastFile != null) {
                BeanDeserializer beanDeserializer = new BeanDeserializer(lastFile.getAbsolutePath());
                return (HDFSConfiguration) beanDeserializer.getObject();
            } else
                return new HDFSConfiguration();
        } catch (IOException | ClassNotFoundException exception) {
            return new HDFSConfiguration();
        }
    }

    public void setData(HDFSConfiguration data) {
        if(data==null){
            textAreaMTToken.setText(data.getMtToken().trim());
            textFieldHDFSLocation.setText(data.getHdfsLocation());
            textFieldServerName.setText(data.getServerName());
            textFieldServerPort.setText(data.getServerPort());
            textFieldConfigurationName.setText(data.getConfigurationName());
        }else {
            textAreaMTToken.setText("");
            textFieldHDFSLocation.setText("");
            textFieldServerName.setText("");
            textFieldServerPort.setText("");
            textFieldConfigurationName.setText("");
        }
    }

    public void getData(HDFSConfiguration data) {
        data.setMtToken(textAreaMTToken.getText().trim());
        data.setHdfsLocation(textFieldHDFSLocation.getText().trim());
        data.setServerName(textFieldServerName.getText().trim());
        data.setServerPort(textFieldServerPort.getText().trim());
        data.setConfigurationName(textFieldConfigurationName.getText().trim());
    }
}
