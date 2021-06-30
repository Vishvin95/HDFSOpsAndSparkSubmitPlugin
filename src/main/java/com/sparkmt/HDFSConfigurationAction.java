package com.sparkmt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.sparkmt.forms.HDFSConfigurationForm;
import org.jetbrains.annotations.NotNull;

public class HDFSConfigurationAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        HDFSConfigurationForm hdfsConfigurationForm = new HDFSConfigurationForm(project.getBasePath());
        hdfsConfigurationForm.show();
    }
}
