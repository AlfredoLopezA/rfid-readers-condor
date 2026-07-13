package com.condor.rfid.config;

import java.util.UUID;

public class AgentConfig {

    private final String agentName;
    private final String agentVersion;
    private final int httpPort;
    private final String apiKey;

    public AgentConfig() {
        this.agentName = "Condor RFID Agent";
        this.agentVersion = "1.0.0";
        this.httpPort = 8090;
        this.apiKey = UUID.randomUUID().toString();
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAgentVersion() {
        return agentVersion;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public String getApiKey() {
        return apiKey;
    }
}
