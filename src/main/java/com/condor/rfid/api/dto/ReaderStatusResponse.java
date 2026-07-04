package com.condor.rfid.api.dto;

public class ReaderStatusResponse {

    private final boolean connected;
    private final boolean reading;
    private final int tagCount;

    public ReaderStatusResponse(
            boolean connected,
            boolean reading,
            int tagCount) {

        this.connected = connected;
        this.reading = reading;
        this.tagCount = tagCount;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isReading() {
        return reading;
    }

    public int getTagCount() {
        return tagCount;
    }
}