package com.condor.rfid.sdk.impinj;

import com.condor.rfid.sdk.RfidReaderDriver;

import com.impinj.octane.AntennaConfigGroup;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.ReportConfig;
import com.impinj.octane.ReportMode;
import com.impinj.octane.Settings;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImpinjReaderDriver implements RfidReaderDriver {

    private ImpinjReader reader;
    private boolean connected = false;
    private boolean reading = false;
    private int tagPopulationEstimate = 32;

    private final List<String> epcList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void connect(String hostname) throws Exception {
        if (connected) { return; }
        reader = new ImpinjReader();
        reader.connect(hostname);
        Settings settings = reader.queryDefaultSettings();
        settings.setTagPopulationEstimate(tagPopulationEstimate);
        ReportConfig reportConfig = settings.getReport();
        reportConfig.setMode(ReportMode.Individual);
        AntennaConfigGroup antennas = settings.getAntennas();
        antennas.enableAll();
        reader.setTagReportListener((ImpinjReader impinjReader, TagReport tagReport) -> {
            for (Tag tag : tagReport.getTags()) {
                String epc = tag.getEpc().toString().replaceAll("\\s+", "");
                //System.out.println("TAG DETECTADO: " + epc);
                if (!epcList.contains(epc)) { epcList.add(epc); }
            }
        });
        reader.applySettings(settings);
        connected = true;
        System.out.println("Driver Impinj conectado.");
    }

    @Override
    public void startReading() throws Exception {
        if (!connected || reader == null) {
            throw new IllegalStateException("Reader is not connected.");
        }
        if (reading) { return; }
        epcList.clear();
        reader.start();
        reading = true;
    }

    @Override
    public void stopReading() throws Exception {
        if (!connected || reader == null) { return; }
        if (!reading) { return; }
        reader.stop();
        reading = false;
    }

    @Override
    public List<String> getReadTags() {
        synchronized (epcList) {
            return new ArrayList<>(epcList);
        }
    }

    @Override
    public void clearReadTags() {
        epcList.clear();
    }

    @Override
    public void disconnect() throws Exception {
        if (reader == null) {
            connected = false;
            reading = false;
            epcList.clear();
            return;
        }

        if (reading) {
            reader.stop();
            reading = false;
        }

        if (connected) {
            reader.disconnect();
            connected = false;
        }

        reader = null;
        epcList.clear();
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public boolean isReading() {
        return reading;
    }

    @Override
    public void setTagPopulationEstimate(int tagPopulationEstimate) {
        this.tagPopulationEstimate = tagPopulationEstimate;
    }

/*     @Override
    public void setTagPopulationEstimate(int tagPopulationEstimate) throws Exception {
        if (!connected || reader == null) { throw new IllegalStateException("Reader is not connected."); }
        Settings settings = reader.querySettings();
        settings.setTagPopulationEstimate(tagPopulationEstimate);
        reader.applySettings(settings);
    }
 */
}