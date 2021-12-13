package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.IPortfolioCreateResponse;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.InstrumentModel;
import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;
import java.util.jar.JarException;

import static com.example.investingmobileapp.PortfoliosFragment.portfolios;

import org.json.JSONException;
import org.json.JSONObject;

public class AddStockToPortfolio extends AppCompatActivity {

    TextView info;
    String user_id;
    EditText inputCount;
    PortfolioServices services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock_to_portfolio);
        init();

    }

    public void init() {
        services = new PortfolioServices(this);
        info = findViewById(R.id.textInfoStock);
        inputCount=findViewById(R.id.inputCount);
        Bundle arguments = getIntent().getExtras();
        int id = Integer.parseInt(arguments.get("stock-id").toString());
        String name = arguments.get("stock-name").toString();
        String desc = arguments.get("stock-desc").toString();
        float price = (float)arguments.get("stock-price");

        InstrumentModel stock = new InstrumentModel(id, name, desc, price, "stock");
        info.setText(stock.toString());
        Toast.makeText(this, stock.toString(), Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = findViewById(R.id.listPortfolios2);

        PortfolioAdapter.OnPortfolioClickListener stateClickListener = new PortfolioAdapter.OnPortfolioClickListener() {
            @Override
            public void OnPortfolioClick(PortfolioModel state, int position) {
                JSONObject jsonBody = new JSONObject();
                try {
                    int count = Integer.parseInt(inputCount.getText().toString());
                    jsonBody.put("count", count);
                    jsonBody.put("portfolio_id", state.getId());
                    jsonBody.put("stock_id", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                services.addStockToPortfolio(jsonBody, new IPortfolioCreateResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(AddStockToPortfolio.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        Toast.makeText(AddStockToPortfolio.this, message, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };
        PortfolioAdapter adapter = new PortfolioAdapter(this, portfolios, stateClickListener);
        recyclerView.setAdapter(adapter);
    }



}