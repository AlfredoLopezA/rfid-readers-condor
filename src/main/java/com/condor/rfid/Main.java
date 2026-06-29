package com.condor.rfid;

import java.util.Scanner;

import com.condor.rfid.config.ReaderBrand;
import com.condor.rfid.config.ReaderConfig;
import com.condor.rfid.config.ReaderConfigManager;
import com.condor.rfid.service.ReaderSetupService;

public class Main {
    public static void main(String[] args) {
        System.out.println("--------------------------------");
        System.out.println("CONDOR RFID CLIENT");
        System.out.println("Version 1.0");
        System.out.println("--------------------------------");
        ReaderConfigManager configManager = new ReaderConfigManager();
        ReaderSetupService setupService = new ReaderSetupService();
        try {
            if (!configManager.exists()) {
                System.out.println("Initial reader configuration required");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Reader brand [IMPINJ]: ");
                String brandInput = scanner.nextLine();
                if (brandInput == null || brandInput.isBlank()) {
                    brandInput = ReaderBrand.IMPINJ.name();
                }
                System.out.print("Reader IP: ");
                String readerIp = scanner.nextLine();
                ReaderConfig config = setupService.createConfiguration(brandInput, readerIp);
                System.out.println("--------------------------------");
                System.out.println("CONFIGURATION CREATED");
                System.out.println("--------------------------------");
                System.out.println("Brand: " + config.getReaderBrand());
                System.out.println("IP: " + config.getReaderIp());
                System.out.println("Model: " + config.getReaderModel());
                System.out.println("Model Number: " + config.getReaderModelNumber());
                System.out.println("Firmware: " + config.getReaderFirmware());
                System.out.println("Max Antennas: " + config.getReaderMaxAntennas());
                System.out.println("--------------------------------");
                scanner.close();
            } else {
                ReaderConfig config = configManager.load();
                System.out.println("Configuration loaded");
                System.out.println("--------------------------------");
                System.out.println("Brand: " + config.getReaderBrand());
                System.out.println("IP: " + config.getReaderIp());
                System.out.println("Model: " + config.getReaderModel());
                System.out.println("Model Number: " + config.getReaderModelNumber());
                System.out.println("Firmware: " + config.getReaderFirmware());
                System.out.println("Max Antennas: " + config.getReaderMaxAntennas());
                System.out.println("Last Validation: " + config.getLastValidationDate());
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}