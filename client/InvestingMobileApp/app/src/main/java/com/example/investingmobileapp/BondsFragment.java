package com.example.investingmobileapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.BondServices;
import com.example.investingmobileapp.interfaces.IGetInstrumentResponse;
import com.example.investingmobileapp.models.InstrumentModel;

import java.util.ArrayList;


public class BondsFragment extends Fragment {

    ArrayList<InstrumentModel> bonds = new ArrayList<InstrumentModel>();
    public BondsFragment() {
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
        return inflater.inflate(R.layout.fragment_bonds, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setData();
    }

    public void setData(){
        BondServices service = new BondServices(getContext());
        service.getBonds("all", "", new IGetInstrumentResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<InstrumentModel> instrumentModels) {
                RecyclerView recyclerView = getView().findViewById(R.id.listBonds);
                // определяем слушателя нажатия элемента в списке
                InstrumentAdapter.OnInstrumentClickListener stateClickListener = new InstrumentAdapter.OnInstrumentClickListener() {
                    @Override
                    public void OnInstrumentClick(InstrumentModel state, int position) {
                        Intent intent = new Intent(getActivity(), AddBondToPortfolio.class);
                        InstrumentModel stock = new InstrumentModel(state.getId(), state.getName(), state.getDescription(), state.getPrice(), "stock");

                        intent.putExtra("bond-id", state.getId());
                        intent.putExtra("bond-name", state.getName());
                        intent.putExtra("bond-desc", state.getDescription());
                        intent.putExtra("bond-price", state.getPrice());
                        startActivity(intent);
                    }
                };
                InstrumentAdapter adapter = new InstrumentAdapter(getActivity(), instrumentModels, stateClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}