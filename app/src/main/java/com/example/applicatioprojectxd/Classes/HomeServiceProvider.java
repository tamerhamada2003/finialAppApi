package com.example.applicatioprojectxd.Classes;

import com.squareup.picasso.RequestCreator;

import java.io.Serializable;

public class HomeServiceProvider implements Serializable {

    private int id;

    private String nameWork;
    private int idWork;
    private String photo;
    private String details;
    private String createdAt;

    double longitude, latitude;
    private int orderId;

    private String nameUser;


    public HomeServiceProvider(String nameWork, int idWork, String photo, String details, String createdAt,
                               int orderId, String nameUser, double longitude, double latitude) {
        this.nameWork = nameWork;
        this.idWork = idWork;
        this.photo = photo;
        this.details = details;
        this.createdAt = createdAt;
        this.longitude = longitude;
        this.latitude = latitude;
        this.orderId = orderId;
        this.nameUser = nameUser;
    }

    public String getNameUser() {
        return nameUser;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public HomeServiceProvider(String nameWork,
                               int idWork, String details,
                               String createdAt, int orderId) {
        this.nameWork = nameWork;
        this.idWork = idWork;
        this.details = details;
        this.createdAt = createdAt;
        this.orderId = orderId;

    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
