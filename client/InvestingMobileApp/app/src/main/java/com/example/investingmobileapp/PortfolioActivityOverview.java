package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.BondServices;
import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.RequestServices.StockServices;
import com.example.investingmobileapp.interfaces.IGetInstrumentResponse;
import com.example.investingmobileapp.interfaces.ISimpleResponse;
import com.example.investingmobileapp.models.InstrumentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioActivityOverview extends AppCompatActivity {

    String portfolio_id;
    String goal;
    String years;
    TextView tvInfo;
    Button btnSend;
    RecyclerView rvListStocks;
    RecyclerView rvListBonds;
    EditText message;
    String messageStr;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_overview);
        init();
    }
    public void init() {
        Bundle arguments = getIntent().getExtras();
        portfolio_id = arguments.get("portfolio_id").toString();
        status = arguments.get("status").toString();

        goal = arguments.get("goal").toString();
        years = arguments.get("years").toString();
        tvInfo = findViewById(R.id.portInfo);
        btnSend = findViewById(R.id.btnSendMessage);
        rvListStocks = findViewById(R.id.listStockPortfolio);
        rvListBonds = findViewById(R.id.listBondPortfolio);
        message = findViewById(R.id.inputMessage);

        if (status.equalsIgnoreCase("Client")) {
           // message.setVisibility(View.GONE);
            findViewById(R.id.textViewMessage).setVisibility(View.GONE);
            btnSend.setOnClickListener(view -> sendPortfolio());
        } else if (status.equalsIgnoreCase("Analyst")) {
            messageStr = message.getText().toString();
            btnSend.setOnClickListener(view -> sendMessage(messageStr));
        }
        setStocks();
        setBonds();
    }

    public void setStocks(){
        StockServices service = new StockServices(this);
        service.getStocks("portfolio", portfolio_id, new IGetInstrumentResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                // определяем слушателя нажатия элемента в списке
                InstrumentAdapter.OnInstrumentClickListener stateClickListener = new InstrumentAdapter.OnInstrumentClickListener() {
                    @Override
                    public void OnInstrumentClick(InstrumentModel state, int position) {

                    }
                };
                InstrumentAdapter adapter = new InstrumentAdapter(PortfolioActivityOverview.this, instrumentModels, stateClickListener);
                rvListStocks.setAdapter(adapter);
            }
        });
    }

    public void setBonds(){
        BondServices service = new BondServices(this);
        service.getBonds("portfolio", portfolio_id, new IGetInstrumentResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                // определяем слушателя нажатия элемента в списке
                InstrumentAdapter.OnInstrumentClickListener stateClickListener = new InstrumentAdapter.OnInstrumentClickListener() {
                    @Override
                    public void OnInstrumentClick(InstrumentModel state, int position) {

                    }
                };
                InstrumentAdapter adapter = new InstrumentAdapter(PortfolioActivityOverview.this, instrumentModels, stateClickListener);
                rvListBonds.setAdapter(adapter);
            }
        });
    }

    public void sendMessage(String messageAnalyst){
        PortfolioServices service = new PortfolioServices(this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("portfolio_id", Integer.parseInt(portfolio_id));
            jsonBody.put("message", messageAnalyst);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        service.sendMessage(jsonBody, new ISimpleResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendPortfolio(){
        PortfolioServices service = new PortfolioServices(this);
        service.sendPortfolio(portfolio_id, new ISimpleResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(PortfolioActivityOverview.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}