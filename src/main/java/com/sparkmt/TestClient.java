package com.sparkmt;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparkmt.beans.SparkJobRequestBody;
import com.sparkmt.utils.SparkCommandParser;

public class TestClient {
    public static void main(String[] args) {
        SparkJobRequestBody sparkJobRequestBody = SparkCommandParser.parse("spark-submit.cmd --master yarn --queue BingAdsAdQuality --deploy-mode cluster --conf \"spark.yarn.stagingDir=%stagingDir%\" --conf \"spark.executor.extraJavaOptions=-XX:ParallelGCThreads=4 -XX:+UseCompressedOops\" --name OffersEventHubToCosmos --driver-memory 4G --executor-memory 2G --executor-cores 4 --num-executors 8 --class Workflows.Streaming.PROD.OffersEventHubToCosmos hdfs://MTPrime-CO4-0/projects/BingAdsAdQuality/PROD/Libs/Spark3/AdQualitySpark3-assembly-0.1.jar --logLocation adl://bingads-algo-prod-adquality-c08.azuredatalakestore.net/local/AdQuality/SparkCheckpoints/OffersEventHubToCosmos/productadstocosmos/ --sourceOption eventHubStream --eventHubMaxEventsPerTrigger 200000 --eventHubSourceConnectionString Endpoint=sb://productadprodv2.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=DdMi3HdugsbHV3jqM6sm/m+GveUOg8eLLMpHJpLueyA= --eventHubSourceName productadstocosmos --eventHubSourceConsumerGroup $DEFAULT --sinkOption file --destinationFilePath adl://bingads-algo-prod-adquality-c08.azuredatalakestore.net/local/AdQuality/SparkCheckpoints/OffersEventHubToCosmos");
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(getExclusionStrategy(sparkJobRequestBody)).create();
        String requestBody = gson.toJson(sparkJobRequestBody);
        System.out.println(requestBody);
        System.out.println(sparkJobRequestBody.toString());
    }

    static ExclusionStrategy getExclusionStrategy(SparkJobRequestBody sparkJobRequestBody) {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                String fieldName = fieldAttributes.getName();
                boolean result;
                switch (fieldName) {
                    case "file":
                        result = sparkJobRequestBody.shouldSerializeFile();
                        break;
                    case "proxyUser":
                        result = sparkJobRequestBody.shouldSerializeProxyUser();
                        break;
                    case "className":
                        result = sparkJobRequestBody.shouldSerializeClassName();
                        break;
                    case "name":
                        result = sparkJobRequestBody.shouldSerializeName();
                        break;
                    case "queue":
                        result = sparkJobRequestBody.shouldSerializeQueue();
                        break;
                    case "args":
                        result = sparkJobRequestBody.shouldSerializeArgs();
                        break;
                    case "jars":
                        result = sparkJobRequestBody.shouldSerializeJars();
                        break;
                    case "pyFiles":
                        result = sparkJobRequestBody.shouldSerializePyFiles();
                        break;
                    case "driverMemory":
                        result = sparkJobRequestBody.shouldSerializeDriverMemory();
                        break;
                    case "numExecutors":
                        result = sparkJobRequestBody.shouldSerializeNumExecutors();
                        break;
                    case "executorCores":
                        result = sparkJobRequestBody.shouldSerializeExecutorCores();
                        break;
                    case "executorMemory":
                        result = sparkJobRequestBody.shouldSerializeExecutorMemory();
                        break;
                    case "conf":
                        result = sparkJobRequestBody.shouldSerializeConf();
                        break;
                    case "driverCores":
                        result = sparkJobRequestBody.shouldSerializeDriverCores();
                        break;
                    case "files":
                        result = sparkJobRequestBody.shouldSerializeFiles();
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
}
