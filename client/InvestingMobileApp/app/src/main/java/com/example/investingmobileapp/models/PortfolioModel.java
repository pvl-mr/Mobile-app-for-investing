package com.example.investingmobileapp.models;

public class PortfolioModel {

    private String goal;
    private int years;
    private int clientId;
    private int id;
    private int analystId;
    private String message;



    public PortfolioModel(int id, String goal, int years, int clientId) {
        this.id = id;
        this.goal = goal;
        this.years = years;
        this.clientId = clientId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnalystId() {
        return analystId;
    }

    public void setAnalystId(int analystId) {
        this.analystId = analystId;
    }

    @Override
    public String toString() {
        String message = (getMessage() == null) ? "" : "\nСообщение от аналитика: " + getMessage();
        return "Цель: " + getGoal() +
                "\nСрок: " + getYears() + message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
