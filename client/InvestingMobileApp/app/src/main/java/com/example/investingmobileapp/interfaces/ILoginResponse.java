package com.example.investingmobileapp.interfaces;

public interface ILoginResponse {

    void onError(String message);

    void onResponse(String userId);

}
