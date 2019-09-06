package com.example.mysmsapp;

public class SmsEntity {


    private String id;
    private String threadId;
    private String address;
    private String type;
    private String date;
    private String msg;


    public SmsEntity(String id, String threadId, String address, String type, String date, String msg) {
        this.id = id;
        this.threadId = threadId;
        this.address = address;
        this.type = type;
        this.date = date;
        this.msg = msg;
    }

    public SmsEntity(String address, String msg) {
        this.address = address;
        this.msg = msg;

        this.id = "";
        this.threadId = "";
        this.type = "";
        this.date = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
