package com.example.daltud2.Model;

public class Comic {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgRepresent() {
        return imgRepresent;
    }

    public void setImgRepresent(int imgRepresent) {
        this.imgRepresent = imgRepresent;
    }

    public String title;
    public int imgRepresent;//link anh hoac id anh trong thu muc

    public Comic(String title,int imgRepresent){
        this.title = title;
        this.imgRepresent = imgRepresent;
    }
}
