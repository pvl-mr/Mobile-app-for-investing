package com.example.investingmobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class PortfoliosFragment extends Fragment {



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

        Bundle arguments = getActivity().getIntent().getExtras();
        Log.d("getAcivity", getActivity().toString());
        Log.d("getI", getActivity().getIntent().toString());
        Log.d("getall", getActivity().getIntent().getExtras().toString());
        String name = arguments.get("user_id").toString();
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_portfolios, container, false);
    }
}