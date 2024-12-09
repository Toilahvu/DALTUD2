package com.example.daltud2.Model;

import java.io.Serializable;

public class ChuongTruyen implements Serializable {
    private String idChapter;
    private String idTruyen;
    private int chuongSo;
    private String ngayPhatHanh;

    public ChuongTruyen(String idChapter, String idTruyen, int chuongSo, String ngayPhatHanh) {
        this.idChapter = idChapter;
        this.idTruyen = idTruyen;
        this.chuongSo = chuongSo;
        this.ngayPhatHanh = ngayPhatHanh;
    }


    // Getter và Setter
    public String getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(String idChapter) {
        this.idChapter = idChapter;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public int getChuongSo() {
        return chuongSo;
    }

    public void setChuongSo(int chuongSo) {
        this.chuongSo = chuongSo;
    }

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }
}

