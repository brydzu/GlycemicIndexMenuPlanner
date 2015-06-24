package com.naelteam.glycemicindexmenuplanner.network.error;

/**
 * Created by fab on 08/06/15.
 */
public class BaseError {

    private String mMessage;

    public BaseError(String message) {
        mMessage = message;
    }

    public String getMessage(){
        return mMessage;
    }
}
