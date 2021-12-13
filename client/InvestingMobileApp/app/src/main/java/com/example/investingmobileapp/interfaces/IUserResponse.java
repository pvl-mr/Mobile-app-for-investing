package com.example.investingmobileapp.interfaces;

import com.example.investingmobileapp.models.UserModel;

public interface IUserResponse {

    void onError(String message);

    void onResponse(UserModel userModel);

}
