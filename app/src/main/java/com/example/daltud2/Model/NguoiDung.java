package com.example.daltud2.Model;

public class NguoiDung {
    private String idUser;
    private String tenUser;
    private String matKhau;
    private String soDienThoai;

    public NguoiDung(String idUser, String tenUser, String matKhau, String soDienThoai, int role) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
    }

    // Getter và Setter
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
