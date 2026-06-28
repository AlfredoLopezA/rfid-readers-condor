package com.condor.rfid.sdk;

public final class TagPopulationCalculator {
    private TagPopulationCalculator() {
    }

    public static int calculate(int estimatedTags) {
        if (estimatedTags <= 0) {
            return 32;
        }
        int population = 32;
        while (population < estimatedTags) {
            population *= 2;
        }
        return population;
    }
}