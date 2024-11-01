package com.example.daltud2.Model;

public class NguoiDung {
    private String idUser;
    private String tenUser;
    private String matKhau;
    private String soDienThoai;
    private String email;
    private int role;

    // Constructor
    public NguoiDung(String idUser, String tenUser, String matKhau, String soDienThoai, String email, int role) {
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.role = role;
    }

    // Getters và Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}