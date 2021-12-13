package com.example.investingmobileapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.StockServices;
import com.example.investingmobileapp.interfaces.IGetInstrumentResponse;
import com.example.investingmobileapp.models.InstrumentModel;

import java.util.ArrayList;


public class StocksFragment extends Fragment {

    ArrayList<InstrumentModel> stocks = new ArrayList<InstrumentModel>();
    public StocksFragment() {
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
        return inflater.inflate(R.layout.fragment_stocks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setData();
    }

    public void setData(){
        StockServices service = new StockServices(getContext());
        service.getStocks(new IGetInstrumentResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                RecyclerView recyclerView = getView().findViewById(R.id.listStocks);
                // определяем слушателя нажатия элемента в списке
                InstrumentAdapter.OnInstrumentClickListener stateClickListener = new InstrumentAdapter.OnInstrumentClickListener() {
                    @Override
                    public void OnInstrumentClick(InstrumentModel state, int position) {

                        Toast.makeText(getActivity().getApplicationContext(), "Был выбран пункт " + state.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                };
                InstrumentAdapter adapter = new InstrumentAdapter(getActivity(), instrumentModels, stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}