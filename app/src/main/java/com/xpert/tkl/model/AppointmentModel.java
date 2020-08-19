package com.xpert.tkl.model;

public class AppointmentModel {
    private String id;
    private String name;
    private String mobile_no;
    private String whatapp_no;
    private String subject_interested;
    private String address;
    private String amount;
    private int img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getWhatapp_no() {
        return whatapp_no;
    }

    public void setWhatapp_no(String whatapp_no) {
        this.whatapp_no = whatapp_no;
    }

    public String getSubject_interested() {
        return subject_interested;
    }

    public void setSubject_interested(String subject_interested) {
        this.subject_interested = subject_interested;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public AppointmentModel(String id, String name, String mobile_no, String whatapp_no, String subject_interested, String address, String amount) {
        this.id = id;
        this.name = name;
        this.mobile_no = mobile_no;
        this.whatapp_no = whatapp_no;
        this.subject_interested = subject_interested;
        this.address = address;
        this.amount = amount;
    }
}
