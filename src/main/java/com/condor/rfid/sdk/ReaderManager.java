package com.condor.rfid.sdk;

import com.condor.rfid.config.ReaderConfiguration;
import com.impinj.octane.ImpinjReader;

public class ReaderManager {
    private final ImpinjReader reader;
    private ReaderStatus status;
    private ReaderConfiguration configuration;
    private ReaderEventManager eventManager;
    public ReaderManager() {
        this.reader = new ImpinjReader();
        this.status = ReaderStatus.DISCONNECTED;
        this.configuration = new ReaderConfiguration();
        this.eventManager = new ReaderEventManager();
    }

    public boolean connect(String readerIp) {
        try {
            configuration.setReaderIp(readerIp);
            reader.connect(readerIp);
            status = ReaderStatus.CONNECTED;
            System.out.println("Reader connected: " + readerIp);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (status == ReaderStatus.CONNECTED) {
                reader.disconnect();
                status = ReaderStatus.DISCONNECTED;
                System.out.println("Reader disconnected");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ImpinjReader getReader() {
        return reader;
    }

    public ReaderStatus getStatus() {
        return status;
    }

    public ReaderConfiguration getConfiguration() {
        return configuration;
    }

    public ReaderEventManager getEventManager() {
        return eventManager;
    }
}