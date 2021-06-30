package com.sparkmt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sparkmt.forms.PasteAndSubmitJob;
import org.jetbrains.annotations.NotNull;

public class SparkSubmitFromCommandAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PasteAndSubmitJob pasteAndSubmitJob = new PasteAndSubmitJob(e.getProject());
        pasteAndSubmitJob.show();
    }
}
