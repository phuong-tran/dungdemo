package com.pt.test;

interface DataController<T extends DataTimeStamp> {
    long autoDeleteInSec();

    void terminate();

    void addData(T data);

    void onData(T data);

}
