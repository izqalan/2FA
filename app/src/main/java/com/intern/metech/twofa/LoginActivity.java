package com.intern.metech.twofa;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final TextView registerHere = findViewById(R.id.registerHere);
        final Button login = findViewById(R.id.login);

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userId = username.getText().toString();
                final String pwd = password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                String authToken = jsonResponse.getString("authToken");
                                String username = jsonResponse.getString("username");
//                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
//                                intent.putExtra("username", username);
//                                intent.putExtra("authToken", authToken);
//                                startActivity(intent);

                                // if have 2FA setup
                                // Bug

                                if(authToken == null)
                                {
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.putExtra("username", username);
                                    intent.putExtra("authToken", authToken);
                                    startActivity(intent);

                                }else{
                                    // goto verify code
                                    Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                                    intent.putExtra("authToken", authToken);
                                    intent.putExtra("username", username);
                                    startActivity(intent);

                                }

                            }else {
                                AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
                                b.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(userId, pwd, responseListener);
                RequestQueue q = Volley.newRequestQueue(LoginActivity.this);
                q.add(loginRequest);
            }
        });

    }
}
