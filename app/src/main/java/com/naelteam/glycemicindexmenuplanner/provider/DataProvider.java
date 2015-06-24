package com.naelteam.glycemicindexmenuplanner.provider;

import com.naelteam.glycemicindexmenuplanner.provider.mt.MTProvider;

/**
 * Created by fab on 31/05/15.
 */
public class DataProvider {

    private static DataProvider mInstance = new DataProvider();
    private static MTProvider mMTProvider;

    public static MTProvider getMTProvider(){
        if (mMTProvider == null) {
            mMTProvider = new MTProvider();
        }
        return mMTProvider;
    }

    public static DataProvider getInstance(){
        return mInstance;
    }

    public void init(){
        mMTProvider = new MTProvider();

    }

    public void destroy(){
        mMTProvider.destroy();
    }

}
