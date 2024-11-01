package com.example.daltud2.Model;

public class Admin {
    private String idAdmin;
    private String idUser;

    public Admin(String idAdmin, String idUser) {
        this.idAdmin = idAdmin;
        this.idUser = idUser;
    }

    // Getter và Setter
    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}

