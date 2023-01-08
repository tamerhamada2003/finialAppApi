package com.example.applicatioprojectxd.Classes;


import java.io.Serializable;

//بيانات واجهه details order
public class ItemDetails implements Serializable {
    private int id;
    private int orderId;
    private String CreatedAt;
    private String nameWork;
    private String photo;
    private String nameItem;
    private String detailsProblem;
    private int providerId;

    public ItemDetails(int orderId, String CreatedAt, String nameWork,
                       String photo, String nameItem, String detailsProblem, int providerId) {
        this.orderId = orderId;
        this.CreatedAt = CreatedAt;
        this.nameWork = nameWork;
        this.photo = photo;
        this.nameItem = nameItem;
        this.detailsProblem = detailsProblem;
        this.providerId = providerId;

    }


    public ItemDetails(int orderId, String createdAt, String nameWork) {
        this.orderId = orderId;
        this.CreatedAt = createdAt;
        this.nameWork = nameWork;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDetailsProblem() {
        return detailsProblem;
    }

    public void setDetailsProblem(String detailsProblem) {
        this.detailsProblem = detailsProblem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUnderWayCreatedAt() {
        return CreatedAt;
    }

    public void setUnderWayCreatedAt(String underWayCreatedAt) {
        this.CreatedAt = underWayCreatedAt;
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }
}
