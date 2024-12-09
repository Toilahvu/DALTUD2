package com.example.daltud2.Model;

import java.io.Serializable;

public class TruyenAddress implements Serializable {
    private String idTruyen;
    private String tenTag;

    public TruyenAddress(String idTruyen, String tenTag) {
        this.idTruyen = idTruyen;
        this.tenTag = tenTag;
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

    @Override
    public String toString() {
        return tenTag; // Trả về tên tag để hiển thị
    }
}
