package com.example.nuttyapp.ui.main.activity.detailactivity;

public class ActivityStorageDetail {
    String id;
    String name;
    String locations;
    String measurement;
    double stock;

    public ActivityStorageDetail(){

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

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
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

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ActivityStorageDetail(String id, String name, String locations, String measurement, double stock) {
        this.id = id;
        this.name = name;
        this.locations = locations;
        this.measurement = measurement;
        this.stock = stock;
    }
}
