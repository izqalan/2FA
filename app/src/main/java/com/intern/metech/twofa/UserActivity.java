package com.intern.metech.twofa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView helloUser = findViewById(R.id.helloUser);
        Button setup = findViewById(R.id.setup);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String authToken = intent.getStringExtra("authToken");
        String msg = "Hello " + username;

        helloUser.setText(msg);

        // make secret key for authenticator
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent userIntent = new Intent(UserActivity.this, SecurityActivity.class);
                userIntent.putExtra("authToken", authToken);
                userIntent.putExtra("username", username);
                startActivity(userIntent);

            }
        });


    }
}
