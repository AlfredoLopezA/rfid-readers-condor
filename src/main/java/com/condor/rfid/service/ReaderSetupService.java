package com.condor.rfid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.condor.rfid.config.ReaderConfig;
import com.condor.rfid.config.ReaderConfigManager;
import com.condor.rfid.sdk.ReaderManager;
import com.impinj.octane.FeatureSet;

public class ReaderSetupService {
    private final ReaderConfigManager configManager;
    public ReaderSetupService() {
        this.configManager = new ReaderConfigManager();
    }

    public boolean configurationExists() {
        return configManager.exists();
    }

    public ReaderConfig createConfiguration(String readerBrand, String readerIp
    ) throws Exception {
        ReaderManager readerManager = new ReaderManager();
        boolean connected = readerManager.connect(readerIp);
        if (!connected) {
            throw new RuntimeException("Unable to connect to reader");
        }

        FeatureSet featureSet = readerManager.getReader().queryFeatureSet();
        ReaderConfig config = new ReaderConfig();
        config.setReaderBrand(readerBrand);
        config.setReaderIp(readerIp);
        config.setReaderModel(featureSet.getModelName());
        config.setReaderModelNumber(String.valueOf(featureSet.getModelNumber()));
        config.setReaderFirmware(featureSet.getFirmwareVersion());
        config.setReaderMaxAntennas((int) featureSet.getAntennaCount());
        config.setLastValidationDate(LocalDateTime.now().toString());
        config.setConnectedAntennas(new ArrayList<>());
        configManager.save(config);
        readerManager.disconnect();
        return config;
    }
}