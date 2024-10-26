package com.example.daltud2.Model;

public class Address {
    private String tenTag;
    private String moTaTag;

    public Address(String tenTag, String moTaTag) {
        this.tenTag = tenTag;
        this.moTaTag = moTaTag;
    }

    // Getter và Setter
    public String getTenTag() {
        return tenTag;
    }

    public void setTenTag(String tenTag) {
        this.tenTag = tenTag;
    }

    public String getMoTaTag() {
        return moTaTag;
    }

    public void setMoTaTag(String moTaTag) {
        this.moTaTag = moTaTag;
    }
}

