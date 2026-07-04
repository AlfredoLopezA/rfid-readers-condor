package com.condor.rfid.config;

public enum ReaderBrand {
    CAEN_RFID("Caen RFID"),
    CHAINWAY("Chainway"),
    HOPLAND("Hopeland"),
    IMPINJ("Impinj"),
    INVENGO("Invengo"),
    THINGMAGIC("ThingMagic"),
    ZEBRA("Zebra");

    private final String description;
    ReaderBrand(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}