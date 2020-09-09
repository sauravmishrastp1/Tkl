package com.xpert.tkl.model;

public class Subsription_plane_model {
    private String id;
    private String title;
    private String price;
    private String type;
    private String cources;
    private String active;
    private String discription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCources() {
        return cources;
    }

    public void setCources(String cources) {
        this.cources = cources;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Subsription_plane_model(String id, String title, String price, String type, String cources, String active, String discription) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.cources = cources;
        this.active = active;
        this.discription = discription;
    }
}
