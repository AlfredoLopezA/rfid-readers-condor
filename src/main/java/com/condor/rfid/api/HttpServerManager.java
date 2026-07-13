package com.condor.rfid.api;

import com.condor.rfid.service.RfidReaderService;
import com.condor.rfid.api.controller.ReaderController;
import com.condor.rfid.api.controller.AgentController;
import com.condor.rfid.api.controller.HealthController;
import com.condor.rfid.config.AgentConfig;

import static com.condor.rfid.api.HttpRequestHelper.requireGet;
import static com.condor.rfid.api.HttpRequestHelper.requirePost;
import static com.condor.rfid.api.HttpResponseHelper.ok;
import static com.condor.rfid.api.HttpResponseHelper.badRequest;
import static com.condor.rfid.api.HttpResponseHelper.notFound;
import static com.condor.rfid.api.HttpResponseHelper.internalError;
import static com.condor.rfid.api.HttpRequestHelper.getHeader;
import static com.condor.rfid.api.HttpRequestHelper.hasValidApiKey;
import static com.condor.rfid.api.HttpResponseHelper.unauthorized;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class HttpServerManager {
    private final RfidReaderService rfidService;
    private boolean running = false;
    private final AgentConfig agentConfig;
    private HttpServer server;
    private final ReaderController readerController;
    private final HealthController healthController;
    private final AgentController agentController;

    public HttpServerManager(RfidReaderService rfidService, AgentConfig agentConfig) {
        this.rfidService = rfidService;
        this.agentConfig = agentConfig;
        this.readerController = new ReaderController(rfidService);
        this.healthController = new HealthController();
        this.agentController = new AgentController(agentConfig);
    }

    public void start() throws Exception {
        if (running) { return; }
        server = HttpServer.create( new InetSocketAddress("0.0.0.0", agentConfig.getHttpPort()), 0);
        server.createContext("/health", exchange -> {
            if (!requireGet(exchange)) { return; }
            ok(exchange, healthController.getHealth());
        });
        server.createContext("/agent/info", exchange -> {
            if (!requireGet(exchange)) { return; }
            ok(exchange, agentController.getInfo());
        });
        server.createContext("/reader/status", exchange -> {
            if (!requireGet(exchange)) { return; }
            ok(exchange, readerController.getStatus());
        });
        server.createContext("/reader/session/start", exchange -> {
            if (!requirePost(exchange)) { return; }
            if (!hasValidApiKey(exchange, agentConfig.getApiKey())) {
                unauthorized(exchange);
                return;
            }
            try {
                ok(exchange, readerController.startSession());
            } catch (Exception e) {
                internalError(exchange, e.getMessage());
            }
        });
        server.createContext("/reader/session/current", exchange -> {
            if (!requireGet(exchange)) { return; }
            Object session = readerController.getCurrentSession();
            if (session == null) {
                notFound(exchange, "There is no RFID session.");
                return;
            }
            ok(exchange, session);
        });
        server.createContext("/reader/session/tags", exchange -> {
            if (!requireGet(exchange)) { return; }
            Object session = readerController.getCurrentSession();
            if (session == null) {
                notFound(exchange, "There is no RFID session.");
                return;
            }
            ok(exchange, session);
        });
        server.createContext("/reader/read", exchange -> {
            if (!requirePost(exchange)) { return; }
            if (!hasValidApiKey(exchange, agentConfig.getApiKey())) {
                unauthorized(exchange);
                return;
            }
            try {
                String sessionId = getHeader(exchange, "X-Session-Id");
                if (sessionId == null || sessionId.isBlank()) {
                    badRequest(exchange, "Missing X-Session-Id header.");
                    return;
                }
                ok(exchange, readerController.readSession(sessionId));
            } catch (Exception e) {
                internalError(exchange, e.getMessage());
            }
        });
        server.createContext("/reader/session/stop", exchange -> {
            if (!requirePost(exchange)) { return; }
            if (!hasValidApiKey(exchange, agentConfig.getApiKey())) {
                unauthorized(exchange);
                return;
            }
            try {
                String sessionId = getHeader(exchange, "X-Session-Id");
                if (sessionId == null || sessionId.isBlank()) {
                    badRequest(exchange, "Missing X-Session-Id header.");
                    return;
                }
                ok(exchange, readerController.stopSession(sessionId));
            } catch (Exception e) {
                internalError(exchange, e.getMessage());
            }
        });
        server.start();
        running = true;
        System.out.println("HTTP Server iniciado en puerto " + agentConfig.getHttpPort() + ".");
    }

    public void stop() {
        if (!running) { return; }
        if (server != null) {
            server.stop(0);
            server = null;
        }
        running = false;
        System.out.println("HTTP Server detenido.");
    }

    public RfidReaderService getRfidService() {
        return rfidService;
    }

    public ReaderController getReaderController() {
        return readerController;
    }

    public HealthController getHealthController() {
        return healthController;
    }

    public AgentController getAgentController() {
        return agentController;
    }

    public boolean isRunning() {
        return running;
    }

}