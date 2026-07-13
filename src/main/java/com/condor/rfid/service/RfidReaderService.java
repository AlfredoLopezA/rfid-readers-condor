package com.condor.rfid.service;

import com.condor.rfid.sdk.RfidReaderDriver;
import com.condor.rfid.sdk.RfidReaderDriverFactory;
import com.condor.rfid.config.FastReadConfig;
import com.condor.rfid.api.dto.ReadQualityResponse;

import java.util.List;
import java.util.ArrayList;

public class RfidReaderService {

    private RfidReaderDriver driver;
    private final RfidSessionService sessionService = new RfidSessionService();
    private final FastReadConfig fastReadConfig = new FastReadConfig();
    private final RfidConvergenceEvaluator convergenceEvaluator = new RfidConvergenceEvaluator(fastReadConfig);
    private int lastPreliminaryTagCount = 0;
    private int lastFinalTagCount = 0;

    private int lastEtp = 32;
    private int lastCycles = 0;
    private boolean lastConverged = false;
    private String lastConvergenceReason = "";
    private long lastReadDurationMillis = 0;

    public void connect(String readerBrand, String hostname) throws Exception {
        if (driver != null && driver.isConnected()) {
            return;
        }

        driver = RfidReaderDriverFactory.create(readerBrand);
        driver.connect(hostname);
    }

    public void startReading() throws Exception {
        ensureConnected();
        driver.startReading();
    }

    public void stopReading() throws Exception {
        ensureConnected();
        driver.stopReading();
    }

    public List<String> getReadTags() {
        if (driver == null) {
            return List.of();
        }

        return driver.getReadTags();
    }

    public void clearReadTags() {
        if (driver != null) {
            driver.clearReadTags();
        }
    }

    public void disconnect() throws Exception {
        if (driver != null) {
            driver.disconnect();
            driver = null;
        }
    }

    public boolean isConnected() {
        return driver != null && driver.isConnected();
    }

    public boolean isReading() {
        return driver != null && driver.isReading();
    }

    private void ensureConnected() {
        if (driver == null || !driver.isConnected()) {
            throw new IllegalStateException("Reader is not connected.");
        }
    }

    public RfidReadingSession startSession() throws Exception {
        ensureConnected();
        driver.clearReadTags();
        return sessionService.startSession();
    }

    public RfidReadingSession stopSession() throws Exception {
        ensureConnected();
        driver.stopReading();
        RfidReadingSession session = sessionService.stopSession();
        for (String epc : driver.getReadTags()) {
            session.addTag(epc);
        }
        return session;
    }

    public RfidReadingSession getCurrentSession() {
        RfidReadingSession session = sessionService.getCurrentSession();
        session.addNewTags(driver.getReadTags());
        return session;
    }

    public boolean hasActiveSession() {
        return sessionService.hasActiveSession();
    }

    public RfidReadingSession stopSession(String sessionId) throws Exception {
        ensureConnected();
        sessionService.validateCurrentSession(sessionId);
        if (driver.isReading()) {
            driver.stopReading();
        }
        RfidReadingSession session = sessionService.stopSession(sessionId);
        for (String epc : driver.getReadTags()) {
            session.addTag(epc);
        }
        return session;
    }

    public RfidReadingSession readSession(String sessionId) throws Exception {
        ensureConnected();
        sessionService.validateCurrentSession(sessionId);
        long readStart = System.currentTimeMillis();
        driver.clearReadTags();
        // Lectura preliminar rápida para estimar población real
        driver.startReading();
        Thread.sleep(fastReadConfig.getPreliminaryReadMillis());
        driver.stopReading();
        int preliminaryCount = driver.getReadTags().size();
        lastPreliminaryTagCount = preliminaryCount;
        int etp = calculateEtp(preliminaryCount);
        lastEtp = etp;
        lastCycles = 0;
        lastConverged = false;
        lastConvergenceReason = "";
        System.out.println("Lectura preliminar tags: " + preliminaryCount);
        System.out.println("ETP calculado: " + etp);
        driver.setTagPopulationEstimate(etp);
        int maxCycles = calculateMaxReadCycles(etp);
        int pauseMillis = calculatePauseBetweenCyclesMillis(etp);
        int finalReadMinMillis = calculateFinalReadMinMillis(etp);
        System.out.println("Ciclos máximos calculados: " + maxCycles);
        System.out.println("Pausa entre ciclos: " + pauseMillis + " ms");
        System.out.println("Lectura mínima por ciclo: " + finalReadMinMillis + " ms");
        List<String> convergentTags = executeConvergentRead(maxCycles, pauseMillis, finalReadMinMillis );
        lastFinalTagCount = convergentTags.size();
        RfidReadingSession session = sessionService.getCurrentSession();
        session.addNewTags(convergentTags);
        lastReadDurationMillis = System.currentTimeMillis() - readStart;
        session.setReadQuality(new ReadQualityResponse(lastEtp, lastCycles, lastConverged, lastConvergenceReason, lastPreliminaryTagCount, lastFinalTagCount, lastReadDurationMillis));
        return session;
    }

    private void waitForStableReading(int finalReadMinMillis) throws InterruptedException {
        int elapsedMillis = 0;
        int lastCount = 0;
        int stableCycles = 0;
        while (elapsedMillis < fastReadConfig.getFinalReadMaxMillis()) {
            Thread.sleep(fastReadConfig.getStabilityCheckIntervalMillis());
            elapsedMillis += fastReadConfig.getStabilityCheckIntervalMillis();
            int currentCount = driver.getReadTags().size();
            if (currentCount > lastCount) {
                lastCount = currentCount;
                stableCycles = 0;
            } else {
                stableCycles++;
            }
            if (elapsedMillis >= finalReadMinMillis && stableCycles >= fastReadConfig.getStableCyclesRequired()) {
                break;
            }
        }
        System.out.println("Lectura final estabilizada en " + elapsedMillis + " ms.");
        System.out.println("Tags finales detectados: " + driver.getReadTags().size());
    }

    private int calculateEtp(int preliminaryTagCount) {
        if (preliminaryTagCount <= 32) { return 32; }
        if (preliminaryTagCount <= 64) { return 64; }
        if (preliminaryTagCount <= 128) { return 128; }
        if (preliminaryTagCount <= 256) { return 256; }
        return 512;
    }

    private RfidReadCycleResult executeReadCycle(int cycleNumber, List<String> accumulatedTags, int finalReadMinMillis) throws Exception {
        driver.clearReadTags();
        driver.startReading();
        waitForStableReading(finalReadMinMillis);
        driver.stopReading();
        List<String> cycleTags = driver.getReadTags();
        List<String> newTags = new ArrayList<>();
        for (String epc : cycleTags) {
            if (!accumulatedTags.contains(epc)) {
                newTags.add(epc);
            }
        }
        return new RfidReadCycleResult(
                cycleNumber,
                cycleTags.size(),
                newTags.size(),
                cycleTags,
                newTags
        );
    }

    private List<String> executeConvergentRead(int maxCycles, int pauseMillis, int finalReadMinMillis) throws Exception {
        List<String> accumulatedTags = new ArrayList<>();
        List<Integer> growthHistory = new ArrayList<>();
        for (int cycle = 1; cycle <= maxCycles; cycle++) {
            RfidReadCycleResult cycleResult = executeReadCycle(cycle, accumulatedTags, finalReadMinMillis);
            int previousAccumulatedCount = accumulatedTags.size();
            double growthPercent = previousAccumulatedCount == 0 ? 100.0 : (cycleResult.getNewTagCount() * 100.0) / previousAccumulatedCount;
            growthHistory.add(cycleResult.getNewTagCount());
            for (String epc : cycleResult.getNewTags()) {
                accumulatedTags.add(epc);
            }
            System.out.println("Ciclo " + cycleResult.getCycleNumber() + " | Tags ciclo: " + cycleResult.getTagCount()
                            + " | Nuevos: " + cycleResult.getNewTagCount() + " | Acumulados: " + accumulatedTags.size()
                            + " | Crecimiento: " + String.format("%.2f", growthPercent) + "%");
            System.out.println("Historial crecimiento: " + growthHistory);
            RfidConvergenceDecision decision = convergenceEvaluator.evaluate(
                    cycle,
                    maxCycles,
                    accumulatedTags.size(),
                    cycleResult.getNewTagCount(),
                    growthPercent,
                    growthHistory
            );
            lastCycles = cycle;
            lastConverged = decision.isConverged();
            lastConvergenceReason = decision.getReason();
            System.out.println("Decisión convergencia: " + decision.getReason());
            if (decision.isConverged()) { break; }
            if (cycle < maxCycles) { Thread.sleep(pauseMillis); }
        }
        return accumulatedTags;
    }

    private int calculateMaxReadCycles(int etp) {
        if (etp <= 32) { return 2; }
        if (etp <= 64) { return 2; }
        if (etp <= 128) { return 3; }
        if (etp <= 256) { return 4; }
        return 5;
    }

    private int calculatePauseBetweenCyclesMillis(int etp) {
        if (etp <= 64) { return 150; }
        if (etp <= 128) { return 250; }
        if (etp <= 256) { return 350; }
        return 500;
    }

    private int calculateFinalReadMinMillis(int etp) {
        if (etp <= 64) { return 1200; }
        if (etp <= 128) { return 1800; }
        if (etp <= 256) { return 2500; }
        return 3500;
    }
}
