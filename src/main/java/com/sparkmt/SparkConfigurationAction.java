package com.sparkmt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sparkmt.forms.SparkConfigurationForm;
import org.jetbrains.annotations.NotNull;

public class SparkConfigurationAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        SparkConfigurationForm sparkConfigurationForm = new SparkConfigurationForm(e.getProject());
        sparkConfigurationForm.show();
    }
}
