package com.condor.rfid.config;

public class AgentConfig {

    private final String agentName;
    private final String agentVersion;
    private final int httpPort;

    public AgentConfig() {
        this.agentName = "Condor RFID Agent";
        this.agentVersion = "1.0.0";
        this.httpPort = 8090;
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
}