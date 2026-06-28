package com.condor.rfid.config;

import java.util.ArrayList;
import java.util.List;

public class ReaderConfiguration {
    private String readerIp;
    private Integer readerMode;
    private Double txPower;
    private Double rxSensitivity;
    private Integer session;
    private Integer searchMode;
    private Integer tagPopulation;
    private List<Short> antennas;
    public ReaderConfiguration() {
        this.antennas = new ArrayList<>();
        this.tagPopulation = 32;
        this.session = 2;
        this.searchMode = 1;
    }

    public String getReaderIp() {
        return readerIp;
    }

    public void setReaderIp(String readerIp) {
        this.readerIp = readerIp;
    }

    public Integer getReaderMode() {
        return readerMode;
    }

    public void setReaderMode(Integer readerMode) {
        this.readerMode = readerMode;
    }

    public Double getTxPower() {
        return txPower;
    }

    public void setTxPower(Double txPower) {
        this.txPower = txPower;
    }

    public Double getRxSensitivity() {
        return rxSensitivity;
    }

    public void setRxSensitivity(Double rxSensitivity) {
        this.rxSensitivity = rxSensitivity;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public Integer getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(Integer searchMode) {
        this.searchMode = searchMode;
    }

    public Integer getTagPopulation() {
        return tagPopulation;
    }

    public void setTagPopulation(Integer tagPopulation) {
        this.tagPopulation = tagPopulation;
    }

    public List<Short> getAntennas() {
        return antennas;
    }

    public void setAntennas(List<Short> antennas) {
        this.antennas = antennas;
    }
}