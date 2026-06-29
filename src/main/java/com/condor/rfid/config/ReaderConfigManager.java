package com.condor.rfid.config;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReaderConfigManager {
    private static final String CONFIG_FILE = "reader-config.json";
    private final ObjectMapper objectMapper;
    public ReaderConfigManager() {
        this.objectMapper = new ObjectMapper();
    }

    public boolean exists() {
        return new File(CONFIG_FILE).exists();
    }

    public ReaderConfig load() throws IOException {
        return objectMapper.readValue(new File(CONFIG_FILE), ReaderConfig.class);
    }

    public void save(ReaderConfig config) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_FILE), config);
    }
}