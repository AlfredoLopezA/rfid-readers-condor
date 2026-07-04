package com.condor.rfid.agent;

import com.condor.rfid.api.HttpServerManager;
import com.condor.rfid.service.RfidReaderService;
import com.condor.rfid.config.AgentConfig;
import com.condor.rfid.config.AgentConfigManager;

public class CondorAgent {

    private final RfidReaderService rfidReaderService;
    private HttpServerManager httpServerManager;
    private final AgentConfigManager agentConfigManager;
    private AgentConfig agentConfig;

    public CondorAgent() {
        this.rfidReaderService = new RfidReaderService();
        this.agentConfigManager = new AgentConfigManager();
    }

    public void start() throws Exception {
        agentConfig = agentConfigManager.loadOrCreateDefault();
        if (httpServerManager == null) {
            httpServerManager = new HttpServerManager(rfidReaderService, agentConfig);
        }
        if (!httpServerManager.isRunning()) {
            httpServerManager.start();
        }
        System.out.println("Condor Agent iniciado.");
        System.out.println("Agent: " + agentConfig.getAgentName());
        System.out.println("Version: " + agentConfig.getAgentVersion());
        System.out.println("HTTP Port: " + agentConfig.getHttpPort());
    }

    public void stop() throws Exception {
        httpServerManager.stop();
        rfidReaderService.disconnect();
        System.out.println("Condor Agent detenido.");
    }

    public RfidReaderService getRfidReaderService() {
        return rfidReaderService;
    }

    public HttpServerManager getHttpServerManager() {
        return httpServerManager;
    }

    public AgentConfig getAgentConfig() {
        return agentConfig;
    }
}