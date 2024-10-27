package com.example.daltud2.Model;

public class Comment {
    private String idComment;
    private String idTruyen;
    private String idUser;
    private String noiDungBinhLuan;
    private String thoiGianBinhLuan;

    public Comment(String idComment, String idTruyen, String idUser, String noiDungBinhLuan, String thoiGianBinhLuan) {
        this.idComment = idComment;
        this.idTruyen = idTruyen;
        this.idUser = idUser;
        this.noiDungBinhLuan = noiDungBinhLuan;
        this.thoiGianBinhLuan = thoiGianBinhLuan;
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

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
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
}

