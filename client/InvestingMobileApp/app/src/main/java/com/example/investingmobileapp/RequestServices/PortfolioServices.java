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
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.interfaces.IRegisterResponse;
import com.example.investingmobileapp.models.InstrumentModel;
import com.example.investingmobileapp.models.PortfolioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/portfolio/";

    Context context;
    String userId;
    JSONObject array;
    ArrayList<PortfolioModel> portfolios = new ArrayList<>();
    public PortfolioServices(Context context) {
        this.context = context;
    }

    public void getPortfolios(String user_id, final IPortfolioResponse getInstrumentResponse){
        String url = LOCALHOST_SERVER + user_id;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("data");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        int id = obj.getInt("id");
                        int years = obj.getInt("years");
                        String goal = obj.getString("goal");
                        int analystid = obj.getInt("analystid");
                        int clientid = obj.getInt("clientid");
                        PortfolioModel temp = new PortfolioModel(id, goal, years, clientid);
                        temp.setAnalystId(analystid);
                        portfolios.add(temp);
                    }
                    getInstrumentResponse.onResponse(portfolios);

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
