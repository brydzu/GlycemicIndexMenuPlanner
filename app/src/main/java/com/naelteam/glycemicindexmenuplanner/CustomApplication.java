package com.naelteam.glycemicindexmenuplanner;

import android.app.Application;

import com.naelteam.glycemicindexmenuplanner.network.VolleyWrapper;
import com.naelteam.glycemicindexmenuplanner.provider.DataProvider;

/**
 * Created by fab on 02/06/15.
 */
public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init(){
        VolleyWrapper.init(getApplicationContext());
        DataProvider.getInstance().init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        DataProvider.getInstance().destroy();
    }
}
