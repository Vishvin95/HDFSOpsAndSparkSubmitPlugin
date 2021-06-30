package com.sparkmt.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sparkmt.beans.FileType;
import com.sparkmt.beans.HDFSConfiguration;
import com.sparkmt.beans.HDFSFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HDFSUtils {

    public static boolean createDirectory(HDFSConfiguration hdfsConfiguration, String newDirectoryName) {
        OperationRequest createDirectoryRequest = new OperationRequest();
        createDirectoryRequest.setRequestMethod("PUT");
        createDirectoryRequest.getParameters().put("op", "MKDIRS");
        createDirectoryRequest.getHeaders().put("Authorization", "Bearer " + hdfsConfiguration.getMtToken());
        createDirectoryRequest.setEndpoint(constructEndpointFromConfiguration(hdfsConfiguration, newDirectoryName));
        try {
            OperationResponse createDirectoryResponse = HttpUtils.sendRequest(createDirectoryRequest);
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(createDirectoryResponse.getBody());
            return jsonObject.get("boolean").getAsBoolean();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
    }

    public static boolean uploadFile(HDFSConfiguration hdfsConfiguration, String absoluteFilePath) throws IOException {
        String filename = absoluteFilePath.substring(1 + absoluteFilePath.lastIndexOf("\\"));
        // Create file request
        OperationRequest createFileRequest = new OperationRequest();
        createFileRequest.setRequestMethod("PUT");
        createFileRequest.getParameters().put("op", "CREATE");
        createFileRequest.getParameters().put("noredirect", "true");
        createFileRequest.getHeaders().put("Content-Length", "0");
        createFileRequest.getHeaders().put("Authorization", "Bearer " + hdfsConfiguration.getMtToken());
        createFileRequest.setEndpoint(constructEndpointFromConfiguration(hdfsConfiguration, filename));

        // Send request and get response
        OperationResponse createFileResponse = HttpUtils.sendRequest(createFileRequest);
        JsonObject createResponse = (JsonObject) JsonParser.parseString(createFileResponse.getBody());
        String uploadRequestEndpoint = createResponse.get("Location").getAsString();

        // Get file content
        File uploadFile = new File(absoluteFilePath);
        byte[] requestBody = Files.readAllBytes(Paths.get(absoluteFilePath).toAbsolutePath());

        OperationRequest uploadFileRequest = new OperationRequest();
        uploadFileRequest.setRequestMethod("PUT");
        uploadFileRequest.setRequestBody(requestBody);
        uploadFileRequest.getHeaders().put("Content-Type", "application/octet-stream");
        uploadFileRequest.getHeaders().put("Content-Length", String.valueOf(uploadFile.length()));
        uploadFileRequest.setEndpoint(uploadRequestEndpoint);

        // Send request with URL
        OperationResponse uploadFileResponse = HttpUtils.sendRequest(uploadFileRequest);
        return (uploadFileResponse.getResponseCode() == 201);
    }

    public static boolean rename(HDFSConfiguration hdfsConfiguration, String oldFilePath, String newFilePath) {
        OperationRequest renameRequest = new OperationRequest();
        renameRequest.setRequestMethod("PUT");
        renameRequest.getParameters().put("op", "RENAME");
        renameRequest.getParameters().put("destination", hdfsConfiguration.getHdfsLocation() + "/" + newFilePath);
        renameRequest.getHeaders().put("Authorization", "Bearer " + hdfsConfiguration.getMtToken());
        renameRequest.setEndpoint(constructEndpointFromConfiguration(hdfsConfiguration, oldFilePath));

        try {
            OperationResponse renameResponse = HttpUtils.sendRequest(renameRequest);
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(renameResponse.getBody());
            return jsonObject.get("boolean").getAsBoolean();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
    }

    public static boolean delete(HDFSConfiguration hdfsConfiguration, String absoluteFilePath, boolean isDirectory) {
        OperationRequest deleteRequest = new OperationRequest();
        deleteRequest.setRequestMethod("DELETE");
        deleteRequest.getParameters().put("op", "DELETE");
        if (isDirectory)
            deleteRequest.getParameters().put("recursive", "true");
        deleteRequest.getHeaders().put("Authorization", "Bearer " + hdfsConfiguration.getMtToken());
        deleteRequest.setEndpoint(constructEndpointFromConfiguration(hdfsConfiguration, absoluteFilePath));

        try {
            OperationResponse deleteResponse = HttpUtils.sendRequest(deleteRequest);
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(deleteResponse.getBody());
            return jsonObject.get("boolean").getAsBoolean();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        }
    }

    public static ArrayList<HDFSFile> listFiles(HDFSConfiguration hdfsConfiguration, String absoluteFilePath) {
        OperationRequest listFilesRequest = new OperationRequest();
        listFilesRequest.setRequestMethod("GET");
        listFilesRequest.getParameters().put("op", "LISTSTATUS");
        listFilesRequest.getHeaders().put("Authorization", "Bearer " + hdfsConfiguration.getMtToken());
        listFilesRequest.setEndpoint(constructEndpointFromConfiguration(hdfsConfiguration, absoluteFilePath));

        try {
            OperationResponse listFilesResponse = HttpUtils.sendRequest(listFilesRequest);
            if (listFilesResponse.getResponseCode() == 200) {
                ArrayList<HDFSFile> hdfsFiles = new ArrayList<>();
                JsonObject fileStatuses = (JsonObject) JsonParser.parseString(listFilesResponse.getBody());
                JsonObject fileStatus = (JsonObject) fileStatuses.get("FileStatuses");
                JsonArray allFiles = fileStatus.get("FileStatus").getAsJsonArray();
                allFiles.forEach(file -> {
                    JsonObject fileDetails = (JsonObject) file;
                    HDFSFile hdfsFile = new HDFSFile();
                    hdfsFile.setPathSuffix(fileDetails.get("pathSuffix").getAsString());
                    hdfsFile.setAccessTime(fileDetails.get("accessTime").getAsLong());
                    hdfsFile.setModificationTime(fileDetails.get("modificationTime").getAsLong());
                    hdfsFile.setGroup(fileDetails.get("group").getAsString());
                    hdfsFile.setPermission(fileDetails.get("permission").getAsInt());
                    hdfsFile.setLength(fileDetails.get("length").getAsInt());
                    hdfsFile.setOwner(fileDetails.get("owner").getAsString());
                    hdfsFile.setBlockSize(fileDetails.get("blockSize").getAsLong());
                    hdfsFile.setReplication(fileDetails.get("replication").getAsInt());
                    hdfsFile.setType(fileDetails.get("type").getAsString().equals("FILE") ? FileType.FILE : FileType.DIRECTORY);
                    hdfsFiles.add(hdfsFile);
                });
                return hdfsFiles;
            } else {
                return null;
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    private static String constructEndpointFromConfiguration(HDFSConfiguration hdfsConfiguration, String hdfsAppendPath) {
        StringBuilder endpoint = new StringBuilder();
        endpoint
                .append("https://")
                .append(hdfsConfiguration.getServerName())
                .append(":")
                .append(hdfsConfiguration.getServerPort())
                .append("/webhdfs/v1")
                .append(hdfsAppendPath.equals("") ? hdfsConfiguration.getHdfsLocation() : hdfsConfiguration.getHdfsLocation() + "/" + hdfsAppendPath.replaceAll("\\s", "%20"));
        return endpoint.toString();
    }
}
