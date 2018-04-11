package com.intern.metech.twofa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SecurityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        TextView secret = findViewById(R.id.secret);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String authToken = intent.getStringExtra("authToken"); // TOKEN FOR COMPARISON
        secret.setText(authToken);

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
                        String key = jsonResponse.getString("authToken");
                        String qrUrl = jsonResponse.getString("qrCodeUrl");
                        if(success)
                        {
                            TextView secret = findViewById(R.id.secret);
                            secret.setText(key);
                            new DownloadImageFromInternet((ImageView) findViewById(R.id.qr))
                                    .execute(qrUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String qrUrl = jsonResponse.getString("qrCodeUrl");
                        new DownloadImageFromInternet((ImageView) findViewById(R.id.qr))
                                .execute(qrUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        SecretRequest secretRequest = new SecretRequest(username, responseListener);
        RequestQueue q = Volley.newRequestQueue(SecurityActivity.this);
        q.add(secretRequest);

    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
