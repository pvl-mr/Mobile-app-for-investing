package com.example.investingmobileapp.RequestServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.investingmobileapp.helpers.MySingleton;
import com.example.investingmobileapp.interfaces.IGetInstrumentResponse;
import com.example.investingmobileapp.interfaces.IRegisterResponse;
import com.example.investingmobileapp.models.InstrumentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BondServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/";

    Context context;
    String userId;
    JSONObject array;
    ArrayList<InstrumentModel> bonds = new ArrayList<>();
    String url;
    public BondServices(Context context) {
        this.context = context;
    }

    public void getBonds(String status, String portfolioId, final IGetInstrumentResponse getInstrumentResponse){
        if (status.equalsIgnoreCase("all")){
            url = LOCALHOST_SERVER + "bond";
        } else if (status.equalsIgnoreCase("portfolio")) {
            url = LOCALHOST_SERVER+"portfolioBonds/"+ portfolioId;
        } else {
            url = "";
        }

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("data");
                    for (int i = 0; i < arr.length(); i++) {
                        int id;
                        InstrumentModel temp;
                        JSONObject obj = arr.getJSONObject(i);
                        String bond_name = obj.getString("bondname");
                        String bond_desc = obj.getString("bonddesc");
                        float bond_price = (float) obj.getDouble("price");
                        if (status.equalsIgnoreCase("all")) {
                            id = obj.getInt("id");
                            temp = new InstrumentModel(id, bond_name, bond_desc, bond_price, "stock");
                        } else {
                            temp = new InstrumentModel(0, bond_name, bond_desc, bond_price, "stock");
                        }
                        bonds.add(temp);
                    }
                    getInstrumentResponse.onResponse(bonds);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getInstrumentResponse.onError("Не удалось найти облигации");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }
}
