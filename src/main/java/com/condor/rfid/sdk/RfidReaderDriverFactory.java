package com.condor.rfid.sdk;

import com.condor.rfid.sdk.impinj.ImpinjReaderDriver;

public final class RfidReaderDriverFactory {

    private RfidReaderDriverFactory() {
    }

    public static RfidReaderDriver create(String readerBrand) {
        if ("IMPINJ".equalsIgnoreCase(readerBrand)) {
            return new ImpinjReaderDriver();
        }
        throw new IllegalArgumentException("Unsupported reader brand: " + readerBrand);
    }
}