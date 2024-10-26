package com.example.daltud2.Model;

public class TruyenAddress {
    private String idTruyenAddress;
    private String idTruyen;
    private String tenTag;

    public TruyenAddress(String idTruyenAddress, String idTruyen, String tenTag) {
        this.idTruyenAddress = idTruyenAddress;
        this.idTruyen = idTruyen;
        this.tenTag = tenTag;
    }

    // Getter và Setter
    public String getIdTruyenAddress() {
        return idTruyenAddress;
    }

    public void setIdTruyenAddress(String idTruyenAddress) {
        this.idTruyenAddress = idTruyenAddress;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTenTag() {
        return tenTag;
    }

    public void setTenTag(String tenTag) {
        this.tenTag = tenTag;
    }
}

