package com.example.daltud2.Model;

public class NewsFlash {
    private String idNew;
    private String idAdmin;
    private String noiDung;
    private String ngayDang;

    public NewsFlash(String idNew, String idAdmin, String noiDung, String ngayDang) {
        this.idNew = idNew;
        this.idAdmin = idAdmin;
        this.noiDung = noiDung;
        this.ngayDang = ngayDang;
    }

    // Getter và Setter
    public String getIdNew() {
        return idNew;
    }

    public void setIdNew(String idNew) {
        this.idNew = idNew;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }
}

