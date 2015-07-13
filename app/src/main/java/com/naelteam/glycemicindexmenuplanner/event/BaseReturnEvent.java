package com.naelteam.glycemicindexmenuplanner.event;

/**
 * Created by fab on 09/07/15.
 */
public class BaseReturnEvent {

    private Throwable mError;

    public BaseReturnEvent(Throwable error){
        mError = error;
    }

    public Throwable getError(){
        return mError;
    }

}
