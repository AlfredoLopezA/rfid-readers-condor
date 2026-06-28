package com.condor.rfid.sdk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ReaderEventManager {
    private final Set<String> uniqueEpcs;
    public ReaderEventManager() {
        this.uniqueEpcs = new HashSet<>();
    }

    public void clear() {
        uniqueEpcs.clear();
    }

    public boolean addTag(String epc) {
        if (epc == null || epc.isBlank()) {
            return false;
        }
        return uniqueEpcs.add(epc.trim());
    }

    public int getUniqueTagCount() {
        return uniqueEpcs.size();
    }

    public Set<String> getUniqueTags() {
        return Collections.unmodifiableSet(uniqueEpcs);
    }
}