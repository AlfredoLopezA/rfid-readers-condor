package com.condor.rfid.config;

public class FastReadConfig {

    private final int preliminaryReadMillis;
    private final int finalReadMaxMillis;
    private final int finalReadMinMillis;
    private final int stabilityCheckIntervalMillis;
    private final int stableCyclesRequired;
    private final int minReadCycles;
    private final int maxReadCycles;
    private final int pauseBetweenCyclesMillis;
    private final int minimumNewTagsToContinue;    
    private final int estimatedTagPopulation;
    private final double minimumGrowthPercentage;

    public FastReadConfig() {
        this.preliminaryReadMillis = 1000;
        this.finalReadMinMillis = 2000;
        this.finalReadMaxMillis = 5000;
        this.stabilityCheckIntervalMillis = 300;
        this.stableCyclesRequired = 3;
        this.minReadCycles = 2;
        this.maxReadCycles = 5;
        this.pauseBetweenCyclesMillis = 150;
        this.minimumNewTagsToContinue = 1;
        this.estimatedTagPopulation = 32;
        this.minimumGrowthPercentage = 0.5;
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

    public int getMinReadCycles() {
        return minReadCycles;
    }

    public int getMaxReadCycles() {
        return maxReadCycles;
    }

    public int getPauseBetweenCyclesMillis() {
        return pauseBetweenCyclesMillis;
    }

    public int getMinimumNewTagsToContinue() {
        return minimumNewTagsToContinue;
    }

    public int getEstimatedTagPopulation() {
        return estimatedTagPopulation;
    }

    public double getMinimumGrowthPercentage() {
        return minimumGrowthPercentage;
    }
}
