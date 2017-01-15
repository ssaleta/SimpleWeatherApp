package com.example.sebastian.simpleweatherapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by sebastian on 14.01.17.
 */
public class HTTPRequestHandler {

    private RequestQueue requestQueue;
    private static HTTPRequestHandler httpRequestHandler;

    private HTTPRequestHandler(){}

    public static HTTPRequestHandler getInstance(){
        if (httpRequestHandler == null){
            httpRequestHandler = new HTTPRequestHandler();
        }
            return httpRequestHandler;
    }
    public void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }
    public void sendGetRequest(String URL, Response.Listener<String> listener, Response.ErrorListener errorListener){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, listener, errorListener);
        requestQueue.add(stringRequest);
    }
}
