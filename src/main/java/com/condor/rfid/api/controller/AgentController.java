package com.condor.rfid.api.controller;

import com.condor.rfid.api.dto.AgentInfoResponse;
import com.condor.rfid.config.AgentConfig;

public class AgentController {

    private final AgentConfig agentConfig;

    public AgentController(AgentConfig agentConfig) {
        this.agentConfig = agentConfig;
    }

    public AgentInfoResponse getInfo() {
        return new AgentInfoResponse(
                agentConfig.getAgentName(),
                agentConfig.getAgentVersion(),
                System.getProperty("os.name")
        );
    }
}
