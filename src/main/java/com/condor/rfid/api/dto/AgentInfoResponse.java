package com.condor.rfid.api.dto;

public class AgentInfoResponse {

    private final String name;
    private final String version;
    private final String operatingSystem;

    public AgentInfoResponse(
            String name,
            String version,
            String operatingSystem) {

        this.name = name;
        this.version = version;
        this.operatingSystem = operatingSystem;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
}