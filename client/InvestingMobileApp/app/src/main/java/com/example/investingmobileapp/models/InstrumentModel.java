package com.example.investingmobileapp.models;

public class InstrumentModel {
    private String name;
    private String description;
    private String type;
    private float price;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InstrumentModel(int id, String name, String description, float price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
        this.type = type;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Название: " + getName() + "\n"
                + "Описание: " + getDescription() + "\n"
                + "Цена: " + getPrice();
    }
}
