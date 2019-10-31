package com.example.nuttyapp.ui.main.activity;

public class activity {

    String name;
    String descriptions;

    public activity(String name, String descriptions) {
        this.name = name;
        this.descriptions = descriptions;
    }

    public activity(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }



}
