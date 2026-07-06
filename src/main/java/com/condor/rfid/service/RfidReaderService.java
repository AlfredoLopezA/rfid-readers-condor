package com.condor.rfid.service;

import com.condor.rfid.sdk.RfidReaderDriver;
import com.condor.rfid.sdk.RfidReaderDriverFactory;
import com.condor.rfid.config.FastReadConfig;

import java.util.List;

public class RfidReaderService {

    private RfidReaderDriver driver;
    private final RfidSessionService sessionService = new RfidSessionService();
    private final FastReadConfig fastReadConfig = new FastReadConfig();

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
/* 
        RfidReadingSession session = sessionService.getCurrentSession();
        if (session != null && driver != null) {
            for (String epc : driver.getReadTags()) {
                session.addTag(epc);
            }
        }
        return session;
 */
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
        driver.clearReadTags();
        // Lectura preliminar rápida para estimar población real
        driver.startReading();
        Thread.sleep(fastReadConfig.getPreliminaryReadMillis());
        driver.stopReading();
        int preliminaryCount = driver.getReadTags().size();
        int etp = calculateEtp(preliminaryCount);
        System.out.println("Lectura preliminar tags: " + preliminaryCount);
        System.out.println("ETP calculado: " + etp);
        driver.setTagPopulationEstimate(etp);
        driver.clearReadTags();
        // Lectura final con ETP ajustado
        driver.startReading();
        waitForStableReading();
        driver.stopReading();
        RfidReadingSession session = sessionService.getCurrentSession();
        List<String> currentReadTags = driver.getReadTags();
        session.addNewTags(currentReadTags);
        return session;
    }

    private void waitForStableReading() throws InterruptedException {
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
            if (elapsedMillis >= fastReadConfig.getFinalReadMinMillis()
                    && stableCycles >= fastReadConfig.getStableCyclesRequired()) {
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
}
