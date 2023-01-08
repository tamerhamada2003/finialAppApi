package com.example.applicatioprojectxd.Classes;

// بيانات خاصه بانشاء الطلب وعرضه في واجهه المزود خدمه
public class DataHomeProvider {

    private int idCreateOrder;

    private String location;

    // هنا لعرض الصورة

    String nameCustomer; //اسم الزبون الي عندو مشكله

    private String phone;

    private String serviceType;

    private String created_at;


    public DataHomeProvider(int idCreateOrder, String location, String nameCustomer, String phone, String serviceType, String created_at) {
        this.idCreateOrder = idCreateOrder;
        this.location = location;
        this.nameCustomer = nameCustomer;
        this.phone = phone;
        this.serviceType = serviceType;
        this.created_at = created_at;
    }


    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public int getIdCreateOrder() {
        return idCreateOrder;
    }

    public void setIdCreateOrder(int idCreateOrder) {
        this.idCreateOrder = idCreateOrder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
