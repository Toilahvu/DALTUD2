package com.example.daltud2.Model;

public class Comment {
    private String idComment;
    private String idChapter;
    private String idUser;
    private String noiDungBinhLuan;
    private String thoiGianBinhLuan;

    public Comment(String idComment, String idChapter, String idUser, String noiDungBinhLuan, String thoiGianBinhLuan) {
        this.idComment = idComment;
        this.idChapter = idChapter;
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

    public String getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(String idChapter) {
        this.idChapter = idChapter;
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

