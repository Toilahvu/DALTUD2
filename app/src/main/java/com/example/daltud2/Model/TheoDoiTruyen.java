package com.example.daltud2.Model;

public class TheoDoiTruyen {
    private String idTheoDoi;
    private String idTruyen;
    private String idUser;

    public TheoDoiTruyen(String idTheoDoi, String idTruyen, String idUser) {
        this.idTheoDoi = idTheoDoi;
        this.idTruyen = idTruyen;
        this.idUser = idUser;
    }

    // Getter và Setter
    public String getIdTheoDoi() {
        return idTheoDoi;
    }

    public void setIdTheoDoi(String idTheoDoi) {
        this.idTheoDoi = idTheoDoi;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}

