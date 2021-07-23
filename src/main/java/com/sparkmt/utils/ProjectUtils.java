package com.sparkmt.utils;

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

    public static boolean deleteFile(String path){
        File file = new File(path);
        return file.delete();
    }
}
