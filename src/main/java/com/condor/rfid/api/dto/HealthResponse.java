package com.condor.rfid.api.dto;

public class HealthResponse {

    private final boolean alive;
    private final String status;

    public HealthResponse(boolean alive, String status) {
        this.alive = alive;
        this.status = status;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getStatus() {
        return status;
    }
}
