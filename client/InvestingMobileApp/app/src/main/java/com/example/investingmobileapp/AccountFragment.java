package com.example.investingmobileapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.UserServices;
import com.example.investingmobileapp.interfaces.IUserResponse;
import com.example.investingmobileapp.models.UserModel;

public class AccountFragment extends Fragment {

    UserServices service;
    TextView tvFirstName;
    TextView tvLastName;
    Button logout;
    public AccountFragment() {
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        logout = getView().findViewById(R.id.buttonLogOut);
        tvFirstName = getView().findViewById(R.id.textViewFirstName);
        tvLastName = getView().findViewById(R.id.textViewLastName);
        getUser();

    }

    private void getUser(){
        Bundle arguments = getActivity().getIntent().getExtras();
        String user_id = arguments.get("user_id").toString();
        Log.d("user_id------", user_id);
        Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
        service = new UserServices(getContext());
        service.getUser(user_id, new IUserResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(UserModel userModel) {
                tvFirstName.setText(userModel.getFirstName());
                tvLastName.setText(userModel.getLastname());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}