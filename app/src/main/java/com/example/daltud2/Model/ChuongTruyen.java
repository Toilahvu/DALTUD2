package com.example.daltud2.Model;

public class ChuongTruyen {
    private String idChapter;
    private String idTruyen;
    private int chuongSo;
    private String ngayPhatHanh;
    private String anhTruyen;

    public ChuongTruyen(String idChapter, String idTruyen, int chuongSo, String ngayPhatHanh, String anhTruyen) {
        this.idChapter = idChapter;
        this.idTruyen = idTruyen;
        this.chuongSo = chuongSo;
        this.ngayPhatHanh = ngayPhatHanh;
        this.anhTruyen = anhTruyen;
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

    public String getAnhTruyen() {
        return anhTruyen;
    }

    public void setAnhTruyen(String anhTruyen) {
        this.anhTruyen = anhTruyen;
    }
}

