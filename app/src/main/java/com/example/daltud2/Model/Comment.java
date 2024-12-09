package com.example.daltud2.Model;

import java.io.Serializable;

public class Comment implements Serializable {
    private String idComment;
    private String idTruyen;
    private String idUser;
    private String nameUser;
    private String noiDungBinhLuan;
    private String thoiGianBinhLuan;

    public Comment(String idComment, String idTruyen, String idUser, String noiDungBinhLuan, String thoiGianBinhLuan, String nameUser) {
        this.idComment = idComment;
        this.idTruyen = idTruyen;
        this.idUser = idUser;
        this.noiDungBinhLuan = noiDungBinhLuan;
        this.thoiGianBinhLuan = thoiGianBinhLuan;
        this.nameUser = nameUser;
    }

    // Getter và Setter
    public String getIdComment() {
        return idComment;
    }

    public void setIdComment(String idComment) {
        this.idComment = idComment;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idChapter) {
        this.idTruyen = idChapter;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNoiDungBinhLuan() {
        return noiDungBinhLuan;
    }

    public void setNoiDungBinhLuan(String noiDungBinhLuan) {
        this.noiDungBinhLuan = noiDungBinhLuan;
    }

    public String getThoiGianBinhLuan() {
        return thoiGianBinhLuan;
    }

    public void setThoiGianBinhLuan(String thoiGianBinhLuan) {
        this.thoiGianBinhLuan = thoiGianBinhLuan;
    }
    public String getNameUser(){
        return nameUser;
    }
    public void setNameUser(String nameUser){
        this.nameUser = nameUser;
    }
}

