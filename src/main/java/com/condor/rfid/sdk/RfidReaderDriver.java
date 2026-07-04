package com.condor.rfid.sdk;

import java.util.List;

public interface RfidReaderDriver {
    void connect(String hostname) throws Exception;
    void startReading() throws Exception;
    void stopReading() throws Exception;
    List<String> getReadTags();
    void clearReadTags();
    void disconnect() throws Exception;
    boolean isConnected();
    boolean isReading();
}