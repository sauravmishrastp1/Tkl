package com.xpert.tkl.model;

public class StudentData {
    private String id;
    private String name;
    private String address;
    private String phone_no;
    private String class_;
    private String subject;
    private String description;
    private String image;
    private String city;
    private String status;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentData(String id, String name, String address, String phone_no, String class_, String subject, String description, String image, String city, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone_no = phone_no;
        this.class_ = class_;
        this.subject = subject;
        this.description = description;
        this.image = image;
        this.city = city;
        this.status = status;
    }
}