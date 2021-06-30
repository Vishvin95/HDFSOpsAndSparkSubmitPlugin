package com.sparkmt.utils;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;

import java.io.File;

public class ProjectUtils {
    public static String[] getFilenamesInDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            return directory.list();
        } else
            return null;
    }

    public static File getLastFilenameInDirectory(String path) {
        File directory = new File(path);
        File[] allFiles = directory.listFiles();
        if (allFiles != null && allFiles.length > 0)
            return allFiles[allFiles.length - 1];
        else
            return null;
    }

    public static boolean checkFileExists(String path) {
        File directory = new File(path);
        return directory.exists();
    }

    public static boolean createDirectory(String path) {
        if (checkFileExists(path))
            return false;
        else {
            File directory = new File(path);
            return directory.mkdir();
        }
    }

    public static Project getCurrentProject(){
        DataContext dataContext = (DataContext) DataManager.getInstance().getDataContextFromFocusAsync();
        Project project = (Project) CommonDataKeys.PROJECT.getData(dataContext);
        return project;
    }

    public static boolean deleteFile(String path){
        File file = new File(path);
        return file.delete();
    }
}
