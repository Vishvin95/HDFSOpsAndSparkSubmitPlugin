package com.sparkmt.forms;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.Node;
import com.sparkmt.utils.BeanDeserializer;
import com.sparkmt.utils.BeanSerializer;
import com.sparkmt.utils.ProjectUtils;
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
        setTitle("HDFS Configuration Form");
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
            if (!ProjectUtils.checkFileExists(projectBasePath + "/hdfs-configs"))
                ProjectUtils.createDirectory(projectBasePath + "/hdfs-configs");
            BeanSerializer<Node> beanSerializer = new BeanSerializer<>(projectBasePath + "/hdfs-configs/" + newHdfsConfigFileName + ".cfg");
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
            File lastFile = ProjectUtils.getLastFilenameInDirectory(projectBasePath + "/hdfs-configs/");
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

    public boolean isModified(HDFSConfiguration data) {
        if (textAreaMTToken.getText() != null ? !textAreaMTToken.getText().equals(data.getMtToken()) : data.getMtToken() != null)
            return true;
        if (textFieldHDFSLocation.getText() != null ? !textFieldHDFSLocation.getText().equals(data.getHdfsLocation()) : data.getHdfsLocation() != null)
            return true;
        if (textFieldServerName.getText() != null ? !textFieldServerName.getText().equals(data.getServerName()) : data.getServerName() != null)
            return true;
        if (textFieldServerPort.getText() != null ? !textFieldServerPort.getText().equals(data.getServerPort()) : data.getServerPort() != null)
            return true;
        return textFieldConfigurationName.getText() != null ? !textFieldConfigurationName.getText().equals(data.getConfigurationName()) : data.getConfigurationName() != null;
    }
}