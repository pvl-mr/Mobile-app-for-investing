package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;

public class AnalystMainActivity extends AppCompatActivity {

    Button logout;
    String user_id;
    PortfolioServices service = new PortfolioServices(this);
    public static ArrayList<PortfolioModel> portfolioModels
            = new ArrayList<PortfolioModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyst_main);
        Bundle arguments = getIntent().getExtras();
        user_id = arguments.get("user_id").toString();
        logout = findViewById(R.id.btnLogoutAnalyst);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnalystMainActivity.this, LoginActivity.class));
            }
        });
        service.getPortfolios("analyst", user_id, new IPortfolioResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(AnalystMainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<PortfolioModel> portfolios) {
                RecyclerView recyclerView = findViewById(R.id.listPortfoliosAnayst);
                Toast.makeText(AnalystMainActivity.this, portfolios.toString(), Toast.LENGTH_SHORT).show();
                Log.d("2803402", portfolios.toString());
                PortfolioAdapter.OnPortfolioClickListener stateClickListener = new PortfolioAdapter.OnPortfolioClickListener() {
                    @Override
                    public void OnPortfolioClick(PortfolioModel state, int position) {
                        String portfolioId = state.getId()+"";
                        String goal = state.getGoal();
                        String years = state.getYears()+"";
                        Intent intent = new Intent(AnalystMainActivity.this, PortfolioActivityOverview.class);
                        intent.putExtra("portfolio_id", portfolioId);
                        intent.putExtra("goal", goal);
                        intent.putExtra("years", years);
                        startActivity(intent);
                    }
                };
                portfolioModels = portfolios;
                PortfolioAdapter adapter = new PortfolioAdapter(AnalystMainActivity.this, portfolios, stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}