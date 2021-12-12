package com.example.investingmobileapp.RequestServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.investingmobileapp.helpers.MySingleton;
import com.example.investingmobileapp.interfaces.ILoginResponse;
import com.example.investingmobileapp.interfaces.IRegisterResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/";

    Context context;
    String userId;

    public UserServices(Context context) {
        this.context = context;
    }

    public void login(JSONObject body, final ILoginResponse loginResponse) {
        String url = LOCALHOST_SERVER + "login";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                userId = "";
                Log.d("user_id", response.toString());
                try {
                    userId = response.getString("user_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loginResponse.onResponse(userId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loginResponse.onError("Не удалось войти");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

    public void register(JSONObject body, final IRegisterResponse registerResponse){
        String url = LOCALHOST_SERVER + "register";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                registerResponse.onResponse("Вы удачно зарегистрированы!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                registerResponse.onError("Не удалось зарегистрироваться");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }


}
