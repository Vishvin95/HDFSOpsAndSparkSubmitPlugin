package com.sparkmt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sparkmt.forms.ParsedSparkCommandViewer;
import org.jetbrains.annotations.NotNull;

public class SparkParseAndViewAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        ParsedSparkCommandViewer parsedSparkCommandViewer = new ParsedSparkCommandViewer(true);
        parsedSparkCommandViewer.show();
    }
}
