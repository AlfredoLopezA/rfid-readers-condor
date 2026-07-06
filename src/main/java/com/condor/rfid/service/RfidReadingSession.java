package com.condor.rfid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RfidReadingSession {

    private final String sessionId;
    private final LocalDateTime startedAt;
    private LocalDateTime stoppedAt;
    private boolean active;
    private final List<String> tags;
    private List<String> newTags;

    public RfidReadingSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.startedAt = LocalDateTime.now();
        this.active = true;
        this.tags = new ArrayList<>();
        this.newTags = new ArrayList<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    public boolean isActive() {
        return active;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public int getTagCount() {
        return tags.size();
    }

    public void addTag(String epc) {
        if (!tags.contains(epc)) {
            tags.add(epc);
        }
    }

    public void stop() {
        this.active = false;
        this.stoppedAt = LocalDateTime.now();
    }

    public List<String> getNewTags() {
        return new ArrayList<>(newTags);
    }

    public int getNewTagCount() {
        return newTags.size();
    }

    public void addNewTags(List<String> readTags) {
        this.newTags = new ArrayList<>();
        for (String epc : readTags) {
            if (!tags.contains(epc)) {
                this.newTags.add(epc);
                tags.add(epc);
            }
        }
    }
}

