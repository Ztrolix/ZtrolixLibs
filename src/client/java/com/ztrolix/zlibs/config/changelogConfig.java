package com.ztrolix.zlibs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class changelogConfig {
    private static final String CONFIG_FILE_NAME = "ztrolix-libs-changelog.json";
    private final Gson gson;
    private final File configFile;

    public changelogConfig() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        File configDir = new File("config");

        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        configFile = new File(configDir, CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            write(new ConfigData());
        }
    }

    public void write(ConfigData configData) {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            FileWriter writer = new FileWriter(configFile);
            gson.toJson(configData, writer);
            writer.close();
        } catch (IOException ignored) {
        }
    }

    public ConfigData read() {
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, ConfigData.class);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static class ConfigData {
        private int versionInt = 0;

        public int getVersionInt() {
            return versionInt;
        }

        public void setVersionInt(int var) {
            versionInt = var;
        }
    }
}