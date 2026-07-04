package com.condor.rfid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AgentConfigManager {

    private static final String CONFIG_FILE = "agent-config.json";

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public boolean exists() {
        return new File(CONFIG_FILE).exists();
    }

    public AgentConfig load() throws Exception {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, AgentConfig.class);
        }
    }

    public void save(AgentConfig config) throws Exception {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
        }
    }

    public AgentConfig loadOrCreateDefault() throws Exception {
        if (exists()) {
            return load();
        }
        AgentConfig config = new AgentConfig();
        save(config);
        return config;
    }
}