package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.IPortfolioCreateResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class PortfolioActivity extends AppCompatActivity {

    EditText tvgoal, tvyears;
    Button btnOk;
    PortfolioServices service = new PortfolioServices(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        init();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goal = tvgoal.getText().toString();
                Log.d("4431", tvyears.getText().toString());

                int years = Integer.parseInt(tvyears.getText().toString());
                Log.d("goal", goal);
                Log.d("years", years+"");
                Bundle arguments = getIntent().getExtras();
                String user_id = arguments.get("user_id").toString();
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("goal", goal);
                    jsonBody.put("years", years);
                    jsonBody.put("client_id", Integer.parseInt(user_id));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(PortfolioActivity.this, jsonBody.toString(), Toast.LENGTH_SHORT).show();
                Log.d("jsonbody", jsonBody.toString());
                service.createPortfolio(jsonBody, new IPortfolioCreateResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String message) {
                        Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(PortfolioActivity.this, ClientMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }

    public void init(){
        tvgoal = findViewById(R.id.inputGoal);
        tvyears = findViewById(R.id.inputYears);
        btnOk = findViewById(R.id.btnPortOk);
    }
}