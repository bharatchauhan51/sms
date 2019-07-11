package com.example.smarthomesolutions;

public class Details {
    String mailId;
    String devId1;
    String devId2;
    public Details(String mailId, String devId1, String devId2){
        this.mailId=mailId;
        this.devId1=devId1;
        this.devId2=devId2;
    }

    public String getMailId() {
        return mailId;
    }

    public String getDevId1() {
        return devId1;
    }

    public String getDevId2() {
        return devId2;
    }
}
