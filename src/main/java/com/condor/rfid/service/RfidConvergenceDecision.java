package com.condor.rfid.service;

public class RfidConvergenceDecision {

    private final boolean converged;
    private final String reason;

    public RfidConvergenceDecision(boolean converged, String reason) {
        this.converged = converged;
        this.reason = reason;
    }
    public boolean isConverged() { return converged; }
    public String getReason() { return reason; }
}
