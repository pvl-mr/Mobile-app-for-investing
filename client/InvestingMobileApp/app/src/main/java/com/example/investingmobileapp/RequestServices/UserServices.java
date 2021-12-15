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
import com.example.investingmobileapp.interfaces.IUserResponse;
import com.example.investingmobileapp.models.InstrumentModel;
import com.example.investingmobileapp.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/";

    Context context;
    public static String userId = "";
    String status;
    UserModel temp;

    public UserServices(Context context) {
        this.context = context;
    }

    public void login(JSONObject body, final ILoginResponse loginResponse) {
        String url = LOCALHOST_SERVER + "login";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                userId = "";
                loginResponse.onResponse(response);
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

    public void getUser(String user_id, final IUserResponse userResponse) {
        String url = LOCALHOST_SERVER + "client/" + user_id;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int id = response.getInt("id");
                    String first_name = response.getString("first_name");
                    String last_name = response.getString("last_name");
                    String email = response.getString("email");
                    String password = response.getString("password");
                    temp = new UserModel(id, first_name, last_name, email, password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                userResponse.onResponse(temp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userResponse.onError("Не удалось найти пользователя");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

}
