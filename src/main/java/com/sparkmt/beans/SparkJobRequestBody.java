package com.sparkmt.beans;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkJobRequestBody {
    private String file;
    private String proxyUser;
    private String className;
    private String name;
    private String queue;
    private Integer driverCores;
    private String driverMemory;
    private Integer numExecutors;
    private Integer executorCores;
    private String executorMemory;
    private Map<String, String> conf = new HashMap<>();
    private List<String> args = new ArrayList<>();
    private List<String> jars = new ArrayList<>();
    private List<String> pyFiles = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private List<String> archives = new ArrayList<>();


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getDriverMemory() {
        return driverMemory;
    }

    public void setDriverMemory(String driverMemory) {
        this.driverMemory = driverMemory;
    }

    public Integer getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }

    public Integer getExecutorCores() {
        return executorCores;
    }

    public void setExecutorCores(Integer executorCores) {
        this.executorCores = executorCores;
    }

    public String getExecutorMemory() {
        return executorMemory;
    }

    public void setExecutorMemory(String executorMemory) {
        this.executorMemory = executorMemory;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getJars() {
        return jars;
    }

    public void setJars(List<String> jars) {
        this.jars = jars;
    }

    public List<String> getPyFiles() {
        return pyFiles;
    }

    public void setPyFiles(List<String> pyFiles) {
        this.pyFiles = pyFiles;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public boolean shouldSerializeFile() {
        return file != null && !file.isEmpty();
    }

    public boolean shouldSerializeProxyUser() {
        return proxyUser != null && !proxyUser.isEmpty();
    }

    public boolean shouldSerializeClassName() {
        return className != null && !className.isEmpty();
    }

    public boolean shouldSerializeName() {
        return name != null && !name.isEmpty();
    }

    public boolean shouldSerializeQueue() {
        return queue != null && !queue.isEmpty();
    }

    public boolean shouldSerializeDriverMemory() {
        return driverMemory != null && !driverMemory.isEmpty();
    }

    public boolean shouldSerializeNumExecutors() {
        return numExecutors!=null && numExecutors > 0;
    }

    public boolean shouldSerializeExecutorCores() {
        return executorCores!=null && executorCores > 0;
    }

    public boolean shouldSerializeExecutorMemory() {
        return executorMemory != null && !executorMemory.isEmpty();
    }

    public boolean shouldSerializeConf() {
        return conf!=null && conf.size() > 0;
    }

    public boolean shouldSerializeArgs() {
        return args!=null && args.size() > 0;
    }

    public boolean shouldSerializeArchives() {
        return archives!=null && archives.size() > 0;
    }

    public boolean shouldSerializeJars() {
        return jars!=null && jars.size() > 0;
    }

    public boolean shouldSerializePyFiles() {
        return pyFiles!=null && pyFiles.size() > 0;
    }
    
    public boolean shouldSerializeDriverCores() {
        return driverCores!=null && driverCores > 0;
    }
    
    public boolean shouldSerializeFiles() {
        return files!=null && files.size() > 0;
    }

    public void setDriverCores(int driverCores) {
        this.driverCores = driverCores;
    }

    public int getDriverCores() {
        return driverCores;
    }

    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }

    public ExclusionStrategy getExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                String fieldName = fieldAttributes.getName();
                boolean result;
                switch (fieldName) {
                    case "file":
                        result = shouldSerializeFile();
                        break;
                    case "proxyUser":
                        result = shouldSerializeProxyUser();
                        break;
                    case "className":
                        result = shouldSerializeClassName();
                        break;
                    case "name":
                        result = shouldSerializeName();
                        break;
                    case "queue":
                        result = shouldSerializeQueue();
                        break;
                    case "args":
                        result = shouldSerializeArgs();
                        break;
                    case "jars":
                        result = shouldSerializeJars();
                        break;
                    case "pyFiles":
                        result = shouldSerializePyFiles();
                        break;
                    case "driverMemory":
                        result = shouldSerializeDriverMemory();
                        break;
                    case "numExecutors":
                        result = shouldSerializeNumExecutors();
                        break;
                    case "executorCores":
                        result = shouldSerializeExecutorCores();
                        break;
                    case "executorMemory":
                        result = shouldSerializeExecutorMemory();
                        break;
                    case "conf":
                        result = shouldSerializeConf();
                        break;
                    case "driverCores":
                        result = shouldSerializeDriverCores();
                        break;
                    case "files":
                        result = shouldSerializeFiles();
                        break;
                    default:
                        result = false;
                }
                return !result;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };
    }
    
    @Override
    public String toString() {
        return "SparkJobRequestBody{" +
                "file='" + file + '\'' +
                ", proxyUser='" + proxyUser + '\'' +
                ", className='" + className + '\'' +
                ", name='" + name + '\'' +
                ", queue='" + queue + '\'' +
                ", driverCores=" + driverCores +
                ", driverMemory='" + driverMemory + '\'' +
                ", numExecutors=" + numExecutors +
                ", executorCores=" + executorCores +
                ", executorMemory='" + executorMemory + '\'' +
                ", conf=" + conf +
                ", args=" + args +
                ", jars=" + jars +
                ", pyFiles=" + pyFiles +
                ", files=" + files +
                ", archives=" + archives +
                '}';
    }
}
