package com.condor.rfid.api.controller;

import com.condor.rfid.api.dto.HealthResponse;

public class HealthController {

    public HealthResponse getHealth() {
        return new HealthResponse(true, "ONLINE");
    }
}