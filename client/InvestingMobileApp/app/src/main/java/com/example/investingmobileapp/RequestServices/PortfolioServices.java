package com.example.investingmobileapp.RequestServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.investingmobileapp.helpers.MySingleton;
import com.example.investingmobileapp.interfaces.ISimpleResponse;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.PortfolioModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioServices {
    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/portfolio/";
    public static final String LOCALHOST_SERVER_MESSAGE = "http://192.168.0.102:5000/message/";
    public static final String LOCALHOST_SERVER_STOCK = "http://192.168.0.102:5000/portfolioStock";
    public static final String LOCALHOST_SERVER_BOND = "http://192.168.0.102:5000/portfolioBond";
    public static final String LOCALHOST_SERVER_SEND = "http://192.168.0.102:5000/sendPortfolio/";
    public static final String LOCALHOST_SERVER_GET = "http://192.168.0.102:5000/analystPortfolio/";

    Context context;
    String userId;
    JSONObject array;
    ArrayList<PortfolioModel> portfolios = new ArrayList<>();
    public PortfolioServices(Context context) {
        this.context = context;
    }

    public void getPortfolios(String status, String user_id, final IPortfolioResponse getInstrumentResponse){

        String url;
        if (status.equalsIgnoreCase("client")) {
             url = LOCALHOST_SERVER + user_id;
        } else {
            url = LOCALHOST_SERVER_GET + user_id;
        }
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
                        String message = obj.getString("message");
                        PortfolioModel temp = new PortfolioModel(id, goal, years, clientid);
                        if (message != "null") {
                            temp.setMessage(message);
                        }

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
                getInstrumentResponse.onError("Не удалось найти");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

    public void createPortfolio(JSONObject body, final ISimpleResponse portfolioCreateResponse){

        String url = LOCALHOST_SERVER;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                portfolioCreateResponse.onResponse("Портфель добавлен");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jsonbody1", body.toString());
                portfolioCreateResponse.onError(body.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

    public void sendMessage(JSONObject body, final ISimpleResponse portfolioCreateResponse){

        String url = LOCALHOST_SERVER_MESSAGE;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                portfolioCreateResponse.onResponse("Сообщение отправлено");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                portfolioCreateResponse.onError(body.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

    public void addStockToPortfolio(String type, JSONObject body, final ISimpleResponse portfolioCreateResponse){
        String url;
        if (type.equalsIgnoreCase("stock")) {
            url = LOCALHOST_SERVER_STOCK;
        } else {
            url = LOCALHOST_SERVER_BOND;
        }
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                portfolioCreateResponse.onResponse("Инструмент добавлен в портфель");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("093", body.toString());
                portfolioCreateResponse.onError("Инструмент добавить в портфель не удалось");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }

    public void sendPortfolio(String portfolioId, ISimpleResponse portfolioCreateResponse){
        String url = LOCALHOST_SERVER_SEND + portfolioId;
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                portfolioCreateResponse.onResponse("Портфель отправлен");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                portfolioCreateResponse.onError("Портфель отправить не удалось");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObject);
    }


}
