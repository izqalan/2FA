package com.intern.metech.twofa;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VerifyRequest extends StringRequest{

    private static final String VERIFY_REQUEST_URL = "https://toastparent.000webhostapp.com/verify.php";
    private Map<String, String> params;

    public VerifyRequest(String username, String code, String authToken , Response.Listener<String> listener){
        super(Method.POST, VERIFY_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("username", username);
        params.put("code", code);
        params.put("authKey", authToken);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
