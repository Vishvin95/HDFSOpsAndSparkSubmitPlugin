package com.sparkmt.utils.services;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.sparkmt.beans.SparkConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "com.mtspark.SparkConfiguration",
storages = @Storage("sparkConfiguration.xml"))
public class SparkConfigPersistService implements PersistentStateComponent<SparkConfiguration> {

    private SparkConfiguration sparkConfigurationState = new SparkConfiguration();

    @Override
    public @Nullable SparkConfiguration getState() {
        return sparkConfigurationState;
    }

    @Override
    public void loadState(@NotNull SparkConfiguration state) {
        sparkConfigurationState = state;
    }

    public static PersistentStateComponent<SparkConfiguration> getInstance(){
        return ServiceManager.getService(SparkConfigPersistService.class);
    }

}
