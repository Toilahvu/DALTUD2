package com.example.daltud2.Model;

public class Admin {
    private String idAdmin;
    private String idUser;
    private int role;

    public Admin(String idAdmin, String idUser, int role) {
        this.idAdmin = idAdmin;
        this.idUser = idUser;
        this.role = role;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}

