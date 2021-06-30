package com.sparkmt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparkmt.beans.SparkConfiguration;
import com.sparkmt.beans.SparkJobRequestBody;
import com.sparkmt.utils.services.SparkConfigPersistService;

import java.io.IOException;


public class SparkUtils {
    public static OperationResponse submitJob(String command, SparkConfiguration sparkConfiguration) {
        SparkJobRequestBody sparkJobRequestBody = SparkCommandParser.parse(command);
        sparkJobRequestBody.setProxyUser(sparkConfiguration.getUsername());
        sparkJobRequestBody.setName(sparkJobRequestBody.getName() + "_plugin");

        OperationRequest operationRequest = new OperationRequest();
        operationRequest.setRequestMethod("POST");
        operationRequest.getParameters().put("subcluster", sparkConfiguration.getSubcluster());
        operationRequest.getHeaders().put("Authorization", "Bearer " + sparkConfiguration.getMtToken());
        operationRequest.getHeaders().put("Content-Type","application/octet-stream");
        operationRequest.setEndpoint(constructEndpoint(sparkConfiguration));

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(sparkJobRequestBody.getExclusionStrategy()).create();
        String requestBody = gson.toJson(sparkJobRequestBody);
        operationRequest.setRequestBody(requestBody.getBytes());

        try {
            OperationResponse sparkSubmitResponse = HttpUtils.sendRequest(operationRequest);
            return sparkSubmitResponse;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    private static String constructEndpoint(SparkConfiguration sparkConfiguration) {
        StringBuilder endpoint = new StringBuilder();
        return endpoint
                .append("https://")
                .append(sparkConfiguration.getServer())
                .append(":")
                .append(sparkConfiguration.getPort())
                .append("/batches")
                .toString();
    }

    public static OperationResponse submitJob(String command) {
        SparkConfiguration sparkConfiguration = SparkConfigPersistService.getInstance().getState();
        return submitJob(command, sparkConfiguration);
    }
}
