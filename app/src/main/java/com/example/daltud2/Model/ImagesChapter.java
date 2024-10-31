package com.example.daltud2.Model;

public class ImagesChapter {
    private int idAnh;
    private String idChapter;
    private String urlAnh;

    // Constructor
    public ImagesChapter(int idAnh, String idChapter, String urlAnh) {
        this.idAnh = idAnh;
        this.idChapter = idChapter;
        this.urlAnh = urlAnh;
    }

    // Getters và Setters
    public int getIdAnh() {
        return idAnh;
    }

    public void setIdAnh(int idAnh) {
        this.idAnh = idAnh;
    }

    public String getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(String idChapter) {
        this.idChapter = idChapter;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }
}
