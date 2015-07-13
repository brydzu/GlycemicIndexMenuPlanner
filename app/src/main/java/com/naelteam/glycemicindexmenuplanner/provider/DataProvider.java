package com.naelteam.glycemicindexmenuplanner.provider;

import com.naelteam.glycemicindexmenuplanner.provider.mt.MTProvider;
import com.naelteam.glycemicindexmenuplanner.provider.wik.WikProvider;

/**
 * Created by fab on 31/05/15.
 */
public class DataProvider {

    private static DataProvider mInstance = new DataProvider();
    private static MTProvider mMTProvider;
    private static WikProvider mWikProvider;

    public static DataProvider getInstance(){
        return mInstance;
    }

    public void init(){
        mMTProvider = new MTProvider();
        mWikProvider = new WikProvider();
    }

    public void destroy(){
        mMTProvider.destroy();
        mWikProvider.destroy();
    }

}
