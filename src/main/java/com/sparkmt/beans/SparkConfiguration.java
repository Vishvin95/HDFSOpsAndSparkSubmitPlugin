package com.sparkmt.beans;

import com.intellij.openapi.ui.ValidationInfo;

public class SparkConfiguration {
    private String server;
    private String port;
    private String subcluster;
    private String username;
    private String mtToken;

    public SparkConfiguration() {

    }


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }


    public String getSubcluster() {
        return subcluster;
    }

    public void setSubcluster(String subcluster) {
        this.subcluster = subcluster;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMtToken() {
        return mtToken;
    }

    public void setMtToken(String mtToken) {
        this.mtToken = mtToken;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public ValidationInfo validate() {
        return new ValidationInfo("Error");
    }
}
