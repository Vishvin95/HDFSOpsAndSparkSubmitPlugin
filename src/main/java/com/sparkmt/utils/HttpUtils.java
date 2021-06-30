package com.sparkmt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    static OperationResponse sendRequest(OperationRequest request) throws IOException {
        String endpoint = addParametersToEndpoint(request.getEndpoint(), request.getParameters());
        URL requestUrl = new URL(endpoint);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
        httpURLConnection.setRequestMethod(request.getRequestMethod());

        if (request.getHeaders() != null)
            request.getHeaders().forEach(httpURLConnection::setRequestProperty);

        byte[] requestBodyBytes = request.getRequestBody();
        if (requestBodyBytes != null && requestBodyBytes.length != 0) {
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(requestBodyBytes);
        }

        // Receive response
        StringBuilder responseBody = new StringBuilder();
        String output;
        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK ||
                httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
            BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream())));
            while ((output = br.readLine()) != null)
                responseBody.append(output);
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            while ((output = br.readLine()) != null)
                responseBody.append(output);
        }
        return new OperationResponse(httpURLConnection.getResponseCode(), responseBody.toString());
    }

    private static String addParametersToEndpoint(String endpoint, HashMap<String, String> parameters) {
        if (parameters == null || parameters.isEmpty())
            return endpoint.replaceAll("\\s", "%20");

        StringBuilder finalEndpoint = new StringBuilder(endpoint);
        finalEndpoint.append("?");
        for (Map.Entry<String, String> queryParam : parameters.entrySet()) {
            finalEndpoint
                    .append(queryParam.getKey())
                    .append("=")
                    .append(queryParam.getValue())
                    .append("&");
        }
        finalEndpoint.deleteCharAt(finalEndpoint.length() - 1);
        return finalEndpoint.toString().replaceAll("\\s", "%20");
    }
}
