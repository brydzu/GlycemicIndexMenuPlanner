package com.naelteam.glycemicindexmenuplanner.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by fab on 02/06/15.
 */
public class VolleyWrapper {

    private static final VolleyWrapper mInstance = new VolleyWrapper();
    private RequestQueue mRequestQueue;

    public static VolleyWrapper getInstance(){
        return mInstance;
    }

    public static void init(Context context){
        getInstance().setRequestQueue(Volley.newRequestQueue(context.getApplicationContext()));
    }

    public void addRequest(Request request){
        mRequestQueue.add(request);
    }

    private void setRequestQueue(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }
}
