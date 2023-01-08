package com.example.applicatioprojectxd.Classes;

public class DataImage {

    private int id;
    private int image;


    public DataImage(int image) {
        this.image = image;
    }


    public DataImage(int id, int image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
