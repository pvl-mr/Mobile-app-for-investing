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

import com.example.investingmobileapp.helpers.RequestHelper;

import org.json.JSONException;
import org.json.JSONObject;

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
        btnRegister = findViewById(R.id.btnRegister);
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
        String firstName = inputFirstName.getText().toString();
        String lastName = inputLastName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String code = inputCode.getText().toString();
        RequestHelper requestHelper = new RequestHelper();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("first_name", firstName);
        jsonBody.put("last_name", lastName);
        jsonBody.put("email", email);
        jsonBody.put("pass", password);
        Log.d("code", code);
        final String requestBody = jsonBody.toString();
        requestHelper.postRequest(this, requestBody, "http://192.168.0.102:5000/register");
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}