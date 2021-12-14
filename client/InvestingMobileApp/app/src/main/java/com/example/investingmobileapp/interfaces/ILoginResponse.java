package com.example.investingmobileapp.interfaces;

import org.json.JSONObject;

public interface ILoginResponse {

    void onError(String message);

    void onResponse(JSONObject data);

}
