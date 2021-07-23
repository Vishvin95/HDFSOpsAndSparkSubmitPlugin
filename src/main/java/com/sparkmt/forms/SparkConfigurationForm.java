package com.sparkmt.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.sparkmt.beans.SparkConfiguration;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.utils.services.SparkConfigPersistService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SparkConfigurationForm extends DialogWrapper {
    private JPanel panelSparkConfiguration;
    private JTextField textFieldServer;
    private JTextField textFieldPort;
    private JTextField textFieldSubcluster;
    private JTextArea textAreaMTToken;
    private JLabel labelMTToken;
    private JLabel labelSubcluster;
    private JLabel labelPort;
    private JLabel labelServer;
    private JLabel labelUsername;
    private JTextField textFieldUsername;
    private final Project project;
    private SparkConfiguration sparkConfiguration;

    public SparkConfigurationForm(Project project) {
        super(project);
        this.project = project;

        setData(SparkConfigPersistService.getInstance().getState());
        setTitle(StringConstants.SPARK_CONFIG_DIALOG_TITLE);
        setOKButtonText("Save");
        setSize(500, 400);
        setResizable(false);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panelSparkConfiguration;
    }

    @Override
    protected void doOKAction() {
        sparkConfiguration = new SparkConfiguration();
        getData(sparkConfiguration);
        SparkConfigPersistService.getInstance().loadState(sparkConfiguration);
        super.doOKAction();
    }

    public Project getProject() {
        return project;
    }

    public void setData(SparkConfiguration data) {
        if (data == null) {
            textFieldServer.setText("");
            textFieldPort.setText("");
            textFieldSubcluster.setText("");
            textAreaMTToken.setText("");
            textFieldUsername.setText("");
        } else {
            textFieldServer.setText(data.getServer());
            textFieldPort.setText(data.getPort());
            textFieldSubcluster.setText(data.getSubcluster());
            textAreaMTToken.setText(data.getMtToken());
            textFieldUsername.setText(data.getUsername());
        }

    }

    public void getData(SparkConfiguration data) {
        data.setServer(textFieldServer.getText());
        data.setPort(textFieldPort.getText());
        data.setSubcluster(textFieldSubcluster.getText());
        data.setMtToken(textAreaMTToken.getText());
        data.setUsername(textFieldUsername.getText());
    }
}
