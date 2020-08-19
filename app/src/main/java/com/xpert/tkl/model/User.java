package com.xpert.tkl.model;

public class User {

    private String id,name,phone,image,email;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String id, String name, String phone, String image, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.email = email;
    }
}
