package com.condor.rfid.service;

import com.condor.rfid.sdk.RfidReaderDriver;
import com.condor.rfid.sdk.RfidReaderDriverFactory;

import java.util.List;

public class RfidReaderService {

    private RfidReaderDriver driver;
    private final RfidSessionService sessionService = new RfidSessionService();

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
        RfidReadingSession session = sessionService.startSession();
        driver.clearReadTags();
        driver.startReading();
        return session;
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
        if (session != null && driver != null) {
            for (String epc : driver.getReadTags()) {
                session.addTag(epc);
            }
        }
        return session;
    }

    public boolean hasActiveSession() {
        return sessionService.hasActiveSession();
    }

    public RfidReadingSession stopSession(String sessionId) throws Exception {
        ensureConnected();
        sessionService.validateCurrentSession(sessionId);
        driver.stopReading();
        RfidReadingSession session = sessionService.stopSession(sessionId);
        for (String epc : driver.getReadTags()) {
            session.addTag(epc);
        }
        return session;
    }
}
