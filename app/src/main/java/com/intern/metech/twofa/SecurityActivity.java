package com.intern.metech.twofa;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
                if(authToken.equals("null"))
                {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success)
                        {
                            TextView secret = findViewById(R.id.secret);
                            Intent intent = getIntent();
                            String key = intent.getStringExtra("authToken");
                            secret.setText(key);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        String key = intent.getStringExtra("authToken");
        secret.setText(key);
        SecretRequest secretRequest = new SecretRequest(username, responseListener);
        RequestQueue q = Volley.newRequestQueue(SecurityActivity.this);
        q.add(secretRequest);

    }
}
