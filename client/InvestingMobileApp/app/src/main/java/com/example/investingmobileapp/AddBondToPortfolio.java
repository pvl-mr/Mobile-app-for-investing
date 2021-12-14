package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.investingmobileapp.PortfoliosFragment.portfolios;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.ISimpleResponse;
import com.example.investingmobileapp.models.InstrumentModel;
import com.example.investingmobileapp.models.PortfolioModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AddBondToPortfolio extends AppCompatActivity {

    TextView info;
    EditText inputCount;
    PortfolioServices services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bond_to_portfolio);
        init();
    }

    public void init() {
        services = new PortfolioServices(this);
        info = findViewById(R.id.textInfoBond);
        inputCount=findViewById(R.id.inputCountBonds);
        Bundle arguments = getIntent().getExtras();
        int id = Integer.parseInt(arguments.get("bond-id").toString());
        String name = arguments.get("bond-name").toString();
        String desc = arguments.get("bond-desc").toString();
        float price = (float)arguments.get("bond-price");

        InstrumentModel stock = new InstrumentModel(id, name, desc, price, "bond");
        info.setText(stock.toString());
        Toast.makeText(this, stock.toString(), Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = findViewById(R.id.listPortfolios3);

        PortfolioAdapter.OnPortfolioClickListener stateClickListener = new PortfolioAdapter.OnPortfolioClickListener() {
            @Override
            public void OnPortfolioClick(PortfolioModel state, int position) {
                JSONObject jsonBody = new JSONObject();
                try {
                    int count = Integer.parseInt(inputCount.getText().toString());
                    jsonBody.put("count", count);
                    jsonBody.put("portfolio_id", state.getId());
                    jsonBody.put("bond_id", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                services.addStockToPortfolio("bond", jsonBody, new ISimpleResponse() {
                    @Override
                    public void onError(String message) {
                        Log.d("082", jsonBody.toString());
                        Toast.makeText(AddBondToPortfolio.this, jsonBody.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String message) {
                        Toast.makeText(AddBondToPortfolio.this, message, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };
        PortfolioAdapter adapter = new PortfolioAdapter(this, portfolios, stateClickListener);
        Toast.makeText(this, portfolios.toString(), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
    }
}