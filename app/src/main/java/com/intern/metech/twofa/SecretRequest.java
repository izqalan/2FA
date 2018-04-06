package com.intern.metech.twofa;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SecretRequest extends StringRequest{

    private static final String SECRET_REQUEST_URL = "https://toastparent.000webhostapp.com/generate.php";
    private Map<String, String> params;

    public SecretRequest(String username, Response.Listener<String> listener){
        super(Request.Method.POST, SECRET_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("username", username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
