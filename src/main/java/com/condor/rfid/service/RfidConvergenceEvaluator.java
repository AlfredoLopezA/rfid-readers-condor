package com.condor.rfid.service;

import com.condor.rfid.config.FastReadConfig;

import java.util.List;

public class RfidConvergenceEvaluator {

    private final FastReadConfig fastReadConfig;

    public RfidConvergenceEvaluator(FastReadConfig fastReadConfig) {
        this.fastReadConfig = fastReadConfig;
    }

    public RfidConvergenceDecision evaluate(
            int cycle,
            int maxCycles,
            int accumulatedCount,
            int newTagCount,
            double growthPercent,
            List<Integer> growthHistory) {
        if (cycle < fastReadConfig.getMinReadCycles()) {
            return new RfidConvergenceDecision(false, "Minimum cycles not reached.");
        }
        if (cycle >= maxCycles) {
            return new RfidConvergenceDecision(true, "Maximum cycles reached.");
        }
        if (newTagCount == 0) {
            return new RfidConvergenceDecision(true, "No new tags detected.");
        }
        if (growthPercent < fastReadConfig.getMinimumGrowthPercentage()) {
            return new RfidConvergenceDecision(true, "Growth below threshold.");
        }
        if (isGrowthDecreasing(growthHistory) && growthPercent < 2.0) {
            return new RfidConvergenceDecision(true, "Growth trend decreasing.");
        }
        return new RfidConvergenceDecision(false, "Continue reading.");
    }

    private boolean isGrowthDecreasing(List<Integer> growthHistory) {
        if (growthHistory.size() < 3) { return false; }
        int lastIndex = growthHistory.size() - 1;
        int first = growthHistory.get(lastIndex - 2);
        int second = growthHistory.get(lastIndex - 1);
        int third = growthHistory.get(lastIndex);
        return first >= second && second >= third;
    }
}
