package com.sparkmt.utils;

import com.sparkmt.beans.SparkJobRequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparkCommandParser {
    private SparkCommandParser(){

    }

    public static SparkJobRequestBody parse(String command){
        if(command==null || command.isEmpty())
            return null;

        SparkCommandParser sparkCommandParser = new SparkCommandParser();
        List<String> commandParts = sparkCommandParser.getCommandParts(command);
        return sparkCommandParser.buildPostCommand(commandParts);
    }

    private List<String> getCommandParts(String command) {
        ArrayList<String> commandParts = new ArrayList<>();
        String[] parts = command.split("\\s+");
        Arrays.stream(parts).forEach(part -> {
            if(!part.trim().equals("")){
                commandParts.add(part);
            }
        });
        return commandParts;
    }

    private SparkJobRequestBody buildPostCommand(List<String> commandParts) {
        SparkJobRequestBody sparkJobRequestBody = new SparkJobRequestBody();
        boolean processArgs = false;
        int countOfParts = commandParts.size();
        for(int i=0; i<countOfParts; i++){
            String currentCommandPart = commandParts.get(i);
            if(processArgs){
                addArgs(sparkJobRequestBody, currentCommandPart);
            }
            else if(currentCommandPart.startsWith("--")){
                ArrayList<String> optionValues = new ArrayList<>();
                String optionValue = commandParts.get(i+1);
                optionValues.add(optionValue);
                i++;

                if(currentCommandPart.equals("--conf")){
                    while(commandParts.get(i+1).startsWith("-XX")){
                        optionValues.add(commandParts.get(i+1));
                        i++;
                    }
                }

                addOptions(sparkJobRequestBody, currentCommandPart, optionValues);
            }
            else if(currentCommandPart.endsWith(".jar")){
                addJar(sparkJobRequestBody, currentCommandPart);
                processArgs = true;
            }
        }
        return sparkJobRequestBody;
    }

    private void addOptions(SparkJobRequestBody sparkJobRequestBody, String optionName, ArrayList<String> optionValues) {
        String optionVal = clean(optionValues);
        switch (optionName)
        {
            case "--conf":
                AddConf(sparkJobRequestBody, optionVal);
                break;
            case "--queue":
                sparkJobRequestBody.setQueue(optionVal);
                break;
            case "--driver-memory":
                sparkJobRequestBody.setDriverMemory(optionVal);
                break;
            case "--driver-cores":
                sparkJobRequestBody.setDriverCores(Integer.parseInt(optionVal));
                break;
            case "--executor-cores":
                sparkJobRequestBody.setExecutorCores(Integer.parseInt(optionVal));
                break;
            case "--executor-memory":
                sparkJobRequestBody.setExecutorMemory(optionVal);
                break;
            case "--num-executors":
                sparkJobRequestBody.setNumExecutors(Integer.parseInt(optionVal));
                break;
            case "--name":
                sparkJobRequestBody.setName(optionVal);
                break;
            case "--class":
                sparkJobRequestBody.setClassName(optionVal);
                break;
            case "--archives":
                sparkJobRequestBody.getArchives().add(optionVal);
                break;
            case "--jars":
                sparkJobRequestBody.getJars().add(optionVal);
                break;
            case "--py-files":
                sparkJobRequestBody.getPyFiles().add(optionVal);
                break;
            case "--files":
                sparkJobRequestBody.getFiles().add(optionVal);
                break;
            case "--proxy-user":
                sparkJobRequestBody.setProxyUser(optionVal);
                break;
        }
    }

    private String clean(ArrayList<String> optionValues) {
        StringBuilder finalOptionVal = new StringBuilder();
        optionValues.forEach(optionValue -> {
            finalOptionVal.append(optionValue.trim().replace("\"","")).append(" ");
        });
        return finalOptionVal.toString().trim();
    }

    private void AddConf(SparkJobRequestBody sparkJobRequestBody, String optionVal) {
        int index = optionVal.indexOf('=');
        String[] parts = new String[2];
        parts[0] = optionVal.substring(0, index);
        parts[1] = optionVal.substring(index + 1);

        if (parts.length == 2)
            sparkJobRequestBody.getConf().put(parts[0], parts[1]);
    }

    private void addJar(SparkJobRequestBody sparkJobRequestBody, String jarPath) {
        sparkJobRequestBody.setFile(jarPath);
    }

    private void addArgs(SparkJobRequestBody sparkJobRequestBody, String arg) {
        sparkJobRequestBody.getArgs().add(arg);
    }
}
