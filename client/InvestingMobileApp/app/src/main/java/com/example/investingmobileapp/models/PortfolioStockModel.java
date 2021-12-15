package com.example.investingmobileapp.models;

public class PortfolioStockModel {
    int id;
    int portfolio_id;
    int stock_id;
    int count;

    public PortfolioStockModel(int portfolio_id, int stick_id, int count) {
        this.portfolio_id = portfolio_id;
        this.stock_id = stick_id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
