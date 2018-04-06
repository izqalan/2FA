package com.intern.metech.twofa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        TextView secret = findViewById(R.id.secret);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String authToken = intent.getStringExtra("authToken"); // TOKEN FOR COMPARISON

        // CRASHED  HERE !!

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // generate secret key
                if(authToken == null)
                {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success)
                        {
                            Intent intent = getIntent();
                            final String authToken = intent.getStringExtra("authToken");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        secret.setText(authToken);
        SecretRequest secretRequest = new SecretRequest(username, responseListener);
        RequestQueue q = Volley.newRequestQueue(SecurityActivity.this);
        q.add(secretRequest);

    }
}
