package com.naelteam.glycemicindexmenuplanner.network;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.naelteam.glycemicindexmenuplanner.network.error.AuthentificationError;
import com.naelteam.glycemicindexmenuplanner.network.error.BaseError;
import com.naelteam.glycemicindexmenuplanner.network.error.NetworkError;

/**
 * Created by fab on 07/06/15.
 */
public class VolleyUtils {

    public static BaseError getError(VolleyError volleyError){
        if (volleyError instanceof AuthFailureError){
            return new AuthentificationError(volleyError.getMessage());
        }else {
            return new NetworkError(volleyError.getMessage());
        }
    }
}
