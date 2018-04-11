package com.intern.metech.twofa;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        final EditText code = findViewById(R.id.code);
        final Button verifyButton = findViewById(R.id.verifyButton);
        Intent intent = getIntent();
        final String authToken = intent.getStringExtra("authToken");
        final String username = intent.getStringExtra("username");
        // get secret key to verify code


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kod = code.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {
                                // go back to login to launch profile page
                                Intent intent = new Intent(VerifyActivity.this, UserActivity.class);
                                intent.putExtra("authToken", authToken);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder b = new AlertDialog.Builder(VerifyActivity.this);
                                b.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                VerifyRequest verifyRequest = new VerifyRequest(username, kod, authToken, responseListener);
                RequestQueue q = Volley.newRequestQueue(VerifyActivity.this);
                q.add(verifyRequest);
            }
        });

    }
}
