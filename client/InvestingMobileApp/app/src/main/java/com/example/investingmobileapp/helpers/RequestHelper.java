//package com.example.investingmobileapp.helpers;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.HttpHeaderParser;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.investingmobileapp.interfaces.ILoginResponse;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class RequestHelper {
//    public static final String LOCALHOST_SERVER = "http://192.168.0.102:5000/";
//    private Context context;
//
//    public RequestHelper() {
//    }
//
//    public void postRequest(Context context, String requestBody, String url, ILoginResponse volleyResponseListener){
//        final String[] result = new String[1];
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//           /* JSONObject jsonBody = new JSONObject();
//            jsonBody.put("Title", "Android Volley Demo");
//            jsonBody.put("Author", "BNK");
//            final String requestBody = jsonBody.toString();*/
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOCALHOST_SERVER,
//                response -> Log.i("VOLLEY", response),
//                error -> Log.e("VOLLEY", error.toString())) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//            @Override
//            public byte[] getBody() {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//                    responseString = String.valueOf(response.statusCode);
//                    result[0] = responseString;
//                    // can get more details such as response.headers
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }
//
//        };
//        requestQueue.add(stringRequest);
//        requestQueue.getResponseDelivery();
//
//    }
//
//    public List<JSONObject> volleyPost(Context context, JSONObject postData, String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        List<JSONObject> jsonResponses = new ArrayList<>();
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
//                response -> {
////                        try {
////                        System.out.println(response.getJSONObject("body"));
////                        jsonObject.add(response);
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                    try {
//                        JSONObject jsonObject = response.getJSONObject("data");
//                        jsonResponses.add(jsonObject);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                },
//                error -> error.printStackTrace());
//
//        requestQueue.add(jsonObjectRequest);
//        return jsonResponses;
//    }
//
//}