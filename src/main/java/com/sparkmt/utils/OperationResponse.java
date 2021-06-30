package com.sparkmt.utils;

public class OperationResponse {
    private int responseCode;
    private String body;

    public OperationResponse(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
