package com.pt.test;

public class SampleData extends DataTimeStamp {

    private final String name;
    private final int age;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public SampleData(long timeStampInSec, String name, int age) {
        super(timeStampInSec);
        this.name = name;
        this.age = age;

    }
}
