package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.ISimpleResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class PortfolioActivity extends AppCompatActivity {

    EditText tvgoal, tvyears;
    Button btnOk;
    String user_id;
    String goal;
    int years;
    PortfolioServices service = new PortfolioServices(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        init();
    }

    public void init(){
        Bundle arguments = getIntent().getExtras();
        user_id = arguments.get("user_id").toString();
        tvgoal = findViewById(R.id.inputGoal);
        tvyears = findViewById(R.id.inputYears);
        btnOk = findViewById(R.id.btnPortOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPortfolio();
                Intent intent = new Intent(PortfolioActivity.this, ClientMainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }

    public void createPortfolio(){
        JSONObject jsonBody = createJson();
        service.createPortfolio(jsonBody, new ISimpleResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String message) {
                Toast.makeText(PortfolioActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public JSONObject createJson(){
        goal = tvgoal.getText().toString();
        years = Integer.parseInt(tvyears.getText().toString());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("goal", goal);
            jsonBody.put("years", years);
            jsonBody.put("client_id", Integer.parseInt(user_id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
    }
}