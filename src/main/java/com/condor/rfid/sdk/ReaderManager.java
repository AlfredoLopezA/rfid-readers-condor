package com.condor.rfid.sdk;

public class ReaderManager {
    public boolean connect(String readerIp) {
        System.out.println("Connecting to reader: " + readerIp);
        return true;
    }

    public void disconnect() {
        System.out.println("Reader disconnected");
    }
}