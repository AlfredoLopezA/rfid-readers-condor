package com.condor.rfid.sdk;


import com.impinj.octane.ImpinjReader;

public class ReaderManager {
    private final ImpinjReader reader;
    private ReaderStatus status;
    public ReaderManager() {
        this.reader = new ImpinjReader();
        this.status = ReaderStatus.DISCONNECTED;
    }

    public boolean connect(String readerIp) {
        try {
            reader.connect(readerIp);
            status = ReaderStatus.CONNECTED;
            System.out.println("Reader connected");
            return true;
        } catch (Exception e) {
            System.out.println(
                    e.getMessage()
            );
            return false;
        }
    }

    public void disconnect() {
        try {
            if (status == ReaderStatus.CONNECTED) {
                reader.disconnect();
                status = ReaderStatus.DISCONNECTED;
            }
        } catch (Exception e) {
            System.out.println(
                    e.getMessage()
            );
        }
    }

    public ReaderStatus getStatus() {
        return status;
    }
}