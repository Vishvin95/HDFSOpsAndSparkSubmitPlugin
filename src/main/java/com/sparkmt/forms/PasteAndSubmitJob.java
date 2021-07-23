package com.sparkmt.forms;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.sparkmt.beans.SparkConfiguration;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.utils.OperationResponse;
import com.sparkmt.utils.SparkUtils;
import com.sparkmt.utils.services.SparkConfigPersistService;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PasteAndSubmitJob extends DialogWrapper {
    private JEditorPane editorPaneSparkSubmitCommand;
    private JPanel panelSparkSubmitCommand;
    private Project project;

    public PasteAndSubmitJob(@Nullable Project project) {
        super(project);
        this.project = project;
        setTitle(StringConstants.SPARK_PASTE_AND_SUBMIT_DIALOG_TITLE);
        setResizable(false);
        setOKButtonText(StringConstants.SUBMIT);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panelSparkSubmitCommand;
    }

    @Override
    protected void doOKAction() {
        SwingUtilities.invokeLater(() -> {
            SparkConfiguration sparkConfiguration = SparkConfigPersistService.getInstance().getState();
            OperationResponse status = SparkUtils.submitJob(editorPaneSparkSubmitCommand.getText(), sparkConfiguration);

            NotificationGroup notificationGroup = new NotificationGroup("demo.notifications.balloon",
                    NotificationDisplayType.STICKY_BALLOON, true);

            Notification msg = new Notification(
                    notificationGroup.getDisplayId(),
                    "MT Spark Job", "MT Spark Job Submission Status", status.getBody(),
                    NotificationType.INFORMATION, null);

            msg.notify(project);
        });
        super.doOKAction();
    }
}
