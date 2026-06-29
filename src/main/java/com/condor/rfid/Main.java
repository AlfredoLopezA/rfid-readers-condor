package com.condor.rfid;

import com.condor.rfid.sdk.ReaderManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("--------------------------------");
        System.out.println("CONDOR RFID READER");
        System.out.println("Version 1.0");
        System.out.println("--------------------------------");
        String readerIp = "10.97.15.20";
        ReaderManager readerManager = new ReaderManager();
        boolean connected = readerManager.connect(readerIp);
        if (connected) {
            System.out.println("Connection successful");
            System.out.println("Status: " + readerManager.getStatus());
            readerManager.printReaderInfo();
            readerManager.printReaderSettings();
            readerManager.disconnect();
        } else {
            System.out.println("Connection failed");
        }
    }
}