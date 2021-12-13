package com.example.investingmobileapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.PortfolioServices;
import com.example.investingmobileapp.interfaces.IPortfolioResponse;
import com.example.investingmobileapp.models.PortfolioModel;

import java.util.ArrayList;


public class PortfoliosFragment extends Fragment {

    ArrayList<PortfolioModel> portfolios = new ArrayList<PortfolioModel>();
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
    }

    public void setData(){
        Bundle arguments = getActivity().getIntent().getExtras();
        String user_id = arguments.get("user_id").toString();
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
                PortfolioAdapter adapter = new PortfolioAdapter(getActivity(), portfolioModels, stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}