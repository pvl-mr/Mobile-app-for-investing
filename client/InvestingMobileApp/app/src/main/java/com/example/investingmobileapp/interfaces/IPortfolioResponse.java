package com.example.investingmobileapp.interfaces;

import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;

public interface IPortfolioResponse {
    void onError(String message);

    void onResponse(ArrayList<PortfolioModel> portfolios);
}
