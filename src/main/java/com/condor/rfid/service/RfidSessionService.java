package com.condor.rfid.service;

public class RfidSessionService {
    private RfidReadingSession currentSession;
    public RfidReadingSession startSession() {
        if (currentSession != null && currentSession.isActive()) {
            throw new IllegalStateException("There is already an active RFID session.");
        }
        currentSession = new RfidReadingSession();
        return currentSession;
    }

    public RfidReadingSession stopSession() {
        if (currentSession == null || !currentSession.isActive()) {
            throw new IllegalStateException("There is no active RFID session.");
        }
        currentSession.stop();
        return currentSession;
    }

    public RfidReadingSession getCurrentSession() {
        return currentSession;
    }

    public boolean hasActiveSession() {
        return currentSession != null && currentSession.isActive();
    }

    public void addTagToCurrentSession(String epc) {
        if (currentSession != null && currentSession.isActive()) {
            currentSession.addTag(epc);
        }
    }

    public RfidReadingSession stopSession(String sessionId) {
        if (currentSession == null || !currentSession.isActive()) {
            throw new IllegalStateException("There is no active RFID session.");
        }
        if (!currentSession.getSessionId().equals(sessionId)) {
            throw new IllegalArgumentException("Invalid RFID session id.");
        }
        currentSession.stop();
        return currentSession;
    }

    public void validateCurrentSession(String sessionId) {
        if (currentSession == null || !currentSession.isActive()) {
            throw new IllegalStateException("There is no active RFID session.");
        }
        if (!currentSession.getSessionId().equals(sessionId)) {
            throw new IllegalArgumentException("Invalid RFID session id.");
        }
    }
}
