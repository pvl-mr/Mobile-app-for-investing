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

public class StockServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/";

    Context context;
    String userId;
    JSONObject array;
    ArrayList<InstrumentModel> stocks = new ArrayList<>();
    public StockServices(Context context) {
        this.context = context;
    }

    public void getStocks(final IGetInstrumentResponse getInstrumentResponse){
        String url = LOCALHOST_SERVER + "stock";
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("data");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        int id = obj.getInt("id");
                        String stock_name = obj.getString("stockname");
                        String stock_desc = obj.getString("stockdesc");
                        float stock_price = (float) obj.getDouble("price");
                        InstrumentModel temp = new InstrumentModel(id, stock_name, stock_desc, stock_price, "stock");
                        stocks.add(temp);
                    }
                    getInstrumentResponse.onResponse(stocks);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getInstrumentResponse.onError("Не удалось найти акции");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }
}
