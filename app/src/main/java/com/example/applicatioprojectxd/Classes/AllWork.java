package com.example.applicatioprojectxd.Classes;


public class AllWork {
    private String name;
    private int id;

    public AllWork(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public AllWork(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


