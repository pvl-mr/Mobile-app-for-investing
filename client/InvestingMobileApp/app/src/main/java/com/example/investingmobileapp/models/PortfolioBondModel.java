package com.example.investingmobileapp.models;

public class PortfolioBondModel {
    int ind;
    int portfolio_id;
    int bond_id;
    int count;

    public PortfolioBondModel(int portfolio_id, int bond_id, int count) {
        this.portfolio_id = portfolio_id;
        this.bond_id = bond_id;
        this.count = count;
    }

    public int getInd() {
        return ind;
    }

    public void setId(int ind) {
        this.ind = ind;
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public int getBond_id() {
        return bond_id;
    }

    public void setBond_id(int bond_id) {
        this.bond_id = bond_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
