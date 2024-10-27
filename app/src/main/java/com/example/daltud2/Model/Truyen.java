package com.example.daltud2.Model;

public class Truyen {
    private String idTruyen;
    private String tenTruyen;
    private String tenTacGia;
    private String ngayPhatHanh;
    private String moTaTruyen;
    private String urlAnhBia;

    public Truyen(String idTruyen, String tenTruyen, String tenTacGia, String ngayPhatHanh, String moTaTruyen, String urlAnhBia) {
        this.idTruyen = idTruyen;
        this.tenTruyen = tenTruyen;
        this.tenTacGia = tenTacGia;
        this.ngayPhatHanh = ngayPhatHanh;
        this.moTaTruyen = moTaTruyen;
        this.urlAnhBia = urlAnhBia;
    }

    // Getter và Setter
    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getMoTaTruyen() {
        return moTaTruyen;
    }

    public void setMoTaTruyen(String moTaTruyen) {
        this.moTaTruyen = moTaTruyen;
    }

    public String getUrlAnhBia() {
        return urlAnhBia;
    }

    public void setUrlAnhBia(String urlAnhBia) {
        this.urlAnhBia = urlAnhBia;
    }
}
