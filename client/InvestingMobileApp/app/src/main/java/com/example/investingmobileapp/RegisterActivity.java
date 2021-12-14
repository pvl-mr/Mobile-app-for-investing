package com.example.investingmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investingmobileapp.RequestServices.UserServices;
import com.example.investingmobileapp.interfaces.ILoginResponse;
import com.example.investingmobileapp.interfaces.IRegisterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;

public class RegisterActivity extends AppCompatActivity {

    TextView haveAccount;
    Button btnRegister;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        selListeners();
    }

    public void init() {

        haveAccount = findViewById(R.id.alreadyHaveAccount);
        btnRegister = findViewById(R.id.btnLogin);
        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputCode = findViewById(R.id.inputCode);

    }
    public void selListeners() {
        haveAccount.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
        btnRegister.setOnClickListener(view -> {
            try {
                register(view);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void register(View view) throws JSONException {
        UserServices userServices = new UserServices(this);
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String code = inputCode.getText().toString();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("first_name", firstName);
        jsonBody.put("last_name", lastName);
        jsonBody.put("email", email);
        jsonBody.put("pass", password);
        if (code != "") {
            jsonBody.put("code", code);
        }
        userServices.register(jsonBody, new IRegisterResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String message) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }
}