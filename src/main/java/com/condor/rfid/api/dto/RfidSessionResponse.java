package com.condor.rfid.api.dto;

import java.util.List;

public class RfidSessionResponse {

    private final String sessionId;
    private final boolean active;
    private final String startedAt;
    private final String stoppedAt;
    private final int tagCount;
    private final List<String> tags;
    private final int newTagCount;
    private final List<String> newTags;

    public RfidSessionResponse(
            String sessionId,
            boolean active,
            String startedAt,
            String stoppedAt,
            int tagCount,
            List<String> tags,
            int newTagCount,
            List<String> newTags) {

        this.sessionId = sessionId;
        this.active = active;
        this.startedAt = startedAt;
        this.stoppedAt = stoppedAt;
        this.tagCount = tagCount;
        this.tags = tags;
        this.newTagCount = newTagCount;
        this.newTags = newTags;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isActive() {
        return active;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getStoppedAt() {
        return stoppedAt;
    }

    public int getTagCount() {
        return tagCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getNewTagCount() {
        return newTagCount;
    }

    public List<String> getNewTags() {
        return newTags;
    }
}
