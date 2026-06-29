package com.condor.rfid.config;

import java.util.List;

public class ReaderConfig {
    private String readerBrand;
    private String readerIp;
    private String readerModel;
    private String readerModelNumber;
    private String readerFirmware;
    private Integer readerMaxAntennas;
    private String readerHostname;
    private String lastValidationDate;
    private java.util.List<Short> connectedAntennas;
   
    public String getReaderBrand() {
        return readerBrand;
    }

    public void setReaderBrand(String readerBrand) {
        this.readerBrand = readerBrand;
    }

    public String getReaderIp() {
        return readerIp;
    }

    public void setReaderIp(String readerIp) {
        this.readerIp = readerIp;
    }

    public String getReaderModel() {
        return readerModel;
    }

    public void setReaderModel(String readerModel) {
        this.readerModel = readerModel;
    }

    public String getReaderModelNumber() {
        return readerModelNumber;
    }

    public void setReaderModelNumber(String readerModelNumber) {
        this.readerModelNumber = readerModelNumber;
    }

    public String getReaderFirmware() {
        return readerFirmware;
    }

    public void setReaderFirmware(String readerFirmware) {
        this.readerFirmware = readerFirmware;
    }

    public Integer getReaderMaxAntennas() {
        return readerMaxAntennas;
    }

    public void setReaderMaxAntennas(Integer readerMaxAntennas) {
        this.readerMaxAntennas = readerMaxAntennas;
    }

    public String getReaderHostname() {
        return readerHostname;
    }

    public void setReaderHostname(String readerHostname) {
        this.readerHostname = readerHostname;
    }

    public String getLastValidationDate() {
        return lastValidationDate;
    }

    public void setLastValidationDate(String lastValidationDate) {
        this.lastValidationDate = lastValidationDate;
    }

    public List<Short> getConnectedAntennas() {
        return connectedAntennas;
    }

    public void setConnectedAntennas(List<Short> connectedAntennas) {
        this.connectedAntennas = connectedAntennas;
    }
}
