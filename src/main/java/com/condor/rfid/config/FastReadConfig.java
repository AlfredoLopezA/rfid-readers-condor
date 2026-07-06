package com.condor.rfid.config;

public class FastReadConfig {

    private final int preliminaryReadMillis;
    private final int finalReadMaxMillis;
    private final int finalReadMinMillis;
    private final int stabilityCheckIntervalMillis;
    private final int stableCyclesRequired;
    private final int estimatedTagPopulation;

    public FastReadConfig() {
        this.preliminaryReadMillis = 1000;
        this.finalReadMinMillis = 2000;
        this.finalReadMaxMillis = 5000;
        this.stabilityCheckIntervalMillis = 300;
        this.stableCyclesRequired = 3;
        this.estimatedTagPopulation = 32;
    }

    public int getPreliminaryReadMillis() {
        return preliminaryReadMillis;
    }

    public int getFinalReadMinMillis() {
        return finalReadMinMillis;
    }

    public int getFinalReadMaxMillis() {
        return finalReadMaxMillis;
    }

    public int getStabilityCheckIntervalMillis() {
        return stabilityCheckIntervalMillis;
    }

    public int getStableCyclesRequired() {
        return stableCyclesRequired;
    }

    public int getEstimatedTagPopulation() {
        return estimatedTagPopulation;
    }
}