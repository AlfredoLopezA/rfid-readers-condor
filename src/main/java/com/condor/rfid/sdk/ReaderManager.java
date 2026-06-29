package com.condor.rfid.sdk;

import com.condor.rfid.config.ReaderConfiguration;
import com.impinj.octane.FeatureSet;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Status;
import com.impinj.octane.Settings;

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

    public void printReaderInfo() {
        try {
            if (status != ReaderStatus.CONNECTED) {
                System.out.println("Reader not connected");
                return;
            }
            FeatureSet features = reader.queryFeatureSet();
            Status readerStatus = reader.queryStatus();
            System.out.println("--------------------------------");
            System.out.println("READER INFORMATION");
            System.out.println("--------------------------------");
            System.out.println("Model: " + features.getModelName());
            System.out.println("Model Number: " + features.getModelNumber());
            System.out.println("Firmware: " + features.getFirmwareVersion());
            System.out.println("Antenna Count: " + features.getAntennaCount());
            System.out.println("Connected: " + readerStatus.getIsConnected());
            System.out.println("Temperature: " + readerStatus.getTemperatureCelsius()+ " °C");
            System.out.println("--------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage()
            );
        }
    }

    public void printReaderSettings() {
        try {
            if (status != ReaderStatus.CONNECTED) {
                System.out.println("Reader not connected");
                return;
            }
            Settings settings = reader.querySettings();
            System.out.println("--------------------------------");
            System.out.println("READER SETTINGS");
            System.out.println("--------------------------------");
            System.out.println("Reader Mode: " + settings.getReaderMode());
            System.out.println("Search Mode: " + settings.getSearchMode());
            System.out.println("Session: " + settings.getSession());
            System.out.println("Tag Population: " + settings.getTagPopulationEstimate());
            System.out.println("--------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}