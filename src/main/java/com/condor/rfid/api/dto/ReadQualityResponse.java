package com.condor.rfid.api.dto;

public class ReadQualityResponse {

    private final int etp;
    private final int cycles;
    private final boolean converged;
    private final String reason;
    private final int preliminaryTagCount;
    private final int finalTagCount;
    private final long readDurationMillis;

    public ReadQualityResponse(
            int etp,
            int cycles,
            boolean converged,
            String reason,
            int preliminaryTagCount,
            int finalTagCount,
            long readDurationMillis
        ) {

        this.etp = etp;
        this.cycles = cycles;
        this.converged = converged;
        this.reason = reason;
        this.preliminaryTagCount = preliminaryTagCount;
        this.finalTagCount = finalTagCount;
        this.readDurationMillis = readDurationMillis;
    }

    public int getEtp() { return etp; }
    public int getCycles() { return cycles; }
    public boolean isConverged() { return converged; }
    public String getReason() { return reason; }
    public int getPreliminaryTagCount() { return preliminaryTagCount; }
    public int getFinalTagCount() { return finalTagCount; }
    public long getReadDurationMillis() { return readDurationMillis; }
}
