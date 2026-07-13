package com.condor.rfid.service;

import java.util.List;

public class RfidReadCycleResult {

    private final int cycleNumber;
    private final int tagCount;
    private final int newTagCount;
    private final List<String> tags;
    private final List<String> newTags;

    public RfidReadCycleResult(
            int cycleNumber,
            int tagCount,
            int newTagCount,
            List<String> tags,
            List<String> newTags) {

        this.cycleNumber = cycleNumber;
        this.tagCount = tagCount;
        this.newTagCount = newTagCount;
        this.tags = tags;
        this.newTags = newTags;
    }

    public int getCycleNumber() {
        return cycleNumber;
    }

    public int getTagCount() {
        return tagCount;
    }

    public int getNewTagCount() {
        return newTagCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getNewTags() {
        return newTags;
    }
}