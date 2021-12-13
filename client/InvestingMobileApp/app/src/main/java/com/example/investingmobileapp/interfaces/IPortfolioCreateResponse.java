package com.example.investingmobileapp.interfaces;

import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;

public interface IPortfolioCreateResponse {
    void onError(String message);

    void onResponse(String message);
}
