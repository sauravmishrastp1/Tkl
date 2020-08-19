package com.xpert.tkl.model;

public class BannerSliderModel {

    private String image;
    private String bannerid;


    public BannerSliderModel(String image, String bannerid) {
        this.image = image;
        this.bannerid = bannerid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBannerid() {
        return bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }
}
