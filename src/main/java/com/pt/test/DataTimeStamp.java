package com.pt.test;

abstract class DataTimeStamp {
    private final long timeStampInSec;

    public long getTimeStamp() {
        return timeStampInSec;
    }

    public DataTimeStamp(long timeStampInSec) {
        this.timeStampInSec = timeStampInSec;
    }

    @Override
    public String toString() {
        return "DataTimeStamp{" +
                "timeStampInSec=" + timeStampInSec +
                '}';
    }
}
