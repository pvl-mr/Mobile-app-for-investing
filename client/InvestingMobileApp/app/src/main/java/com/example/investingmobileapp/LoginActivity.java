package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.UserServices;
import com.example.investingmobileapp.interfaces.ILoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    TextView noAccount;
    Button btnLogin;
    UserServices userServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setListeners();
    }

    public void init() {
        noAccount = findViewById(R.id.havenotaccount);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        userServices = new UserServices(LoginActivity.this);
    }

    public void setListeners(){
        noAccount.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        btnLogin.setOnClickListener(view -> {
            try {
                login(view);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void login (View view) throws JSONException {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("pass", password);
        userServices.login(jsonBody, new ILoginResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String userId) {
                Toast.makeText(LoginActivity.this, userId, Toast.LENGTH_SHORT).show();
            }
        });

    }



}