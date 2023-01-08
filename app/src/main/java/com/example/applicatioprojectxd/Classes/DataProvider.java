package com.example.applicatioprojectxd.Classes;

public class DataProvider {
    private  int id;
   private String nameProvider;
    private String photoProvider;


    public DataProvider(String nameProvider, String photoProvider) {
        this.nameProvider = nameProvider;
        this.photoProvider = photoProvider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public String getPhotoProvider() {
        return photoProvider;
    }

    public void setPhotoProvider(String photoProvider) {
        this.photoProvider = photoProvider;
    }
}
