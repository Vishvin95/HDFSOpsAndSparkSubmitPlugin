package com.sparkmt.forms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.sparkmt.beans.SparkJobRequestBody;
import com.sparkmt.constants.StringConstants;
import com.sparkmt.utils.SparkCommandParser;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ParsedSparkCommandViewer extends DialogWrapper {
    private JPanel panelParsedSparkCommandViewer;
    private JTextArea textAreaParsedSparkCommand;
    private JTextArea textAreaSparkCommand;

    public ParsedSparkCommandViewer(boolean canBeParent) {
        super(canBeParent);
        setTitle(StringConstants.SPARK_PARSE_AND_VIEW_DIALOG_TITLE);
        setCancelButtonText(StringConstants.CLOSE);
        setOKButtonText(StringConstants.PARSE);
        setResizable(false);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panelParsedSparkCommandViewer;
    }

    @Override
    protected void doOKAction() {
        String sparkCommand = textAreaSparkCommand.getText();
        SparkJobRequestBody parseSparkCommand = SparkCommandParser.parse(sparkCommand);
        if(parseSparkCommand!=null) {
            Gson gson = new GsonBuilder().addSerializationExclusionStrategy(parseSparkCommand.getExclusionStrategy()).create();
            String parseSparkCommandJson = gson.toJson(parseSparkCommand);
            textAreaParsedSparkCommand.setText(parseSparkCommandJson);
        }else{
            textAreaParsedSparkCommand.setText("");
        }
    }
}
