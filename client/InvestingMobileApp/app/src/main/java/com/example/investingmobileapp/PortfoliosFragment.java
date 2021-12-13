package com.example.investingmobileapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;


public class PortfoliosFragment extends Fragment {

    public static ArrayList<PortfolioModel> portfolios = new ArrayList<PortfolioModel>();
    Button btnAddPort;
    String user_id;
    public PortfoliosFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolios, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setData();
        init();
    }

    public void init(){
        btnAddPort = getView().findViewById(R.id.btnAddPortfolio);
        btnAddPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PortfolioActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }

    public void setData(){
        Bundle arguments = getActivity().getIntent().getExtras();
        user_id = arguments.get("user_id").toString();
        PortfolioServices service = new PortfolioServices(getContext());
        RecyclerView recyclerView = getView().findViewById(R.id.listPortfolios);

        Log.d("view----", getView().toString());
        service.getPortfolios(user_id, new IPortfolioResponse() {

            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<PortfolioModel> portfolioModels) {

                // определяем слушателя нажатия элемента в списке
                PortfolioAdapter.OnPortfolioClickListener stateClickListener = new PortfolioAdapter.OnPortfolioClickListener() {
                    @Override
                    public void OnPortfolioClick(PortfolioModel state, int position) {

                        Toast.makeText(getActivity().getApplicationContext(), "Был выбран пункт " + state.getGoal(),
                                Toast.LENGTH_SHORT).show();
                    }
                };
                portfolios = portfolioModels;
                PortfolioAdapter adapter = new PortfolioAdapter(getActivity(), portfolioModels, stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}