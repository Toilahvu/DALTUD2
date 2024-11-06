package com.example.daltud2.Model;

public class tagComics {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public tagComics(String nameTag,String descriptionTag) {
        this.name = nameTag;
        this.description = descriptionTag;
    }

}
