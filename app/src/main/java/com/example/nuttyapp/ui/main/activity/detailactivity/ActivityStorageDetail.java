package com.example.nuttyapp.ui.main.activity.detailactivity;

public class ActivityStorageDetail {
    private String id;
    private String name;
    private String location;
    private String measurement;
    private double stock;
    private double amount;

    public ActivityStorageDetail() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ActivityStorageDetail(String id, String name, String location, String measurement, double stock) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.measurement = measurement;
        this.stock = stock;
        this.amount = 0;
    }
}
