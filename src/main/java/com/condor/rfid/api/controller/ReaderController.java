package com.condor.rfid.api.controller;

import com.condor.rfid.api.dto.ReaderStatusResponse;
import com.condor.rfid.service.RfidReaderService;
import com.condor.rfid.api.dto.RfidSessionResponse;
import com.condor.rfid.service.RfidReadingSession;

public class ReaderController {

    private final RfidReaderService rfidService;

    public ReaderController(RfidReaderService rfidService) {
        this.rfidService = rfidService;
    }

    public ReaderStatusResponse getStatus() {
        return new ReaderStatusResponse(
                rfidService.isConnected(),
                rfidService.isReading(),
                rfidService.getReadTags().size()
        );
    }

    public RfidSessionResponse startSession() throws Exception {
        return toSessionResponse(rfidService.startSession());
    }

    public RfidSessionResponse stopSession() throws Exception {
        return toSessionResponse(rfidService.stopSession());
    }

    public RfidSessionResponse getCurrentSession() {
        RfidReadingSession session = rfidService.getCurrentSession();
        if (session == null) {
            return null;
        }
        return toSessionResponse(session);
    }

    private RfidSessionResponse toSessionResponse(RfidReadingSession session) {
        return new RfidSessionResponse(
                session.getSessionId(),
                session.isActive(),
                session.getStartedAt().toString(),
                session.getStoppedAt() == null ? null : session.getStoppedAt().toString(),
                session.getTagCount(),
                session.getTags()
        );
    }

    public RfidSessionResponse stopSession(String sessionId) throws Exception {
        return toSessionResponse(rfidService.stopSession(sessionId));
    }
}
