package com.naelteam.glycemicindexmenuplanner.provider;

import android.util.Log;

import de.greenrobot.event.EventBus;

/**
 */
public class DbProvider {

    private final String TAG = DbProvider.class.getSimpleName();

    public DbProvider() {
        //Log.d(TAG, "DbProvider - Register to the EventBus..");
        //EventBus.getDefault().register(this);
    }

    public void destroy() {
        //EventBus.getDefault().unregister(this);
    }
}
