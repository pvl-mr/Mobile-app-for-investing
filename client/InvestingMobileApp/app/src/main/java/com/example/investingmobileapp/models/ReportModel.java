package com.example.investingmobileapp.models;

import android.util.Log;

public class ReportModel {
    private String goal;
    private int years;

    private float percentStocks;
    private float percentBonds;

    private String type;

    public ReportModel(String goal, int years, float percentStocks, float percentBonds) {
        this.goal = goal;
        this.years = years;
        this.percentStocks = percentStocks;
        this.percentBonds = percentBonds;
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

    public int getPercentStocks() {
        return Math.round(percentStocks);
    }

    public void setPercentStocks(float percentStocks) {
        this.percentStocks = percentStocks;
    }

    public int getPercentBonds() {
        return Math.round(percentBonds);
    }

    public void setPercentBonds(float percentBonds) {
        this.percentBonds = percentBonds;
    }

    public String getType() {
        if (getPercentStocks() < 10) {
            return "Осторожный";
        } else if (getPercentStocks() < 20)
            return "Консервативный";
        else if (getPercentStocks() < 30)
            return "Умеренный";
        else
            return "Агрессивный";
    }

    public void setType(String type) {
        this.type = type;
    }
}
