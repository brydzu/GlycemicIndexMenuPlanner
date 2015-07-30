package com.naelteam.glycemicindexmenuplanner;

import android.app.Application;

import com.naelteam.glycemicindexmenuplanner.network.VolleyWrapper;
import com.naelteam.glycemicindexmenuplanner.provider.CouchDBManager;
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
        try {
            CouchDBManager.getInstance().init(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();

            //TODO fatal error, close the app
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        DataProvider.getInstance().destroy();
        CouchDBManager.getInstance().destroy();
    }
}
