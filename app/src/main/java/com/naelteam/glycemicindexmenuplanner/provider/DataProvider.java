package com.naelteam.glycemicindexmenuplanner.provider;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.dao.ProductDao;
import com.naelteam.glycemicindexmenuplanner.event.GetListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.ReturnListGIReturnEvent;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductList;
import com.naelteam.glycemicindexmenuplanner.provider.mt.MTProvider;
import com.naelteam.glycemicindexmenuplanner.provider.wik.WikProvider;
import com.naelteam.glycemicindexmenuplanner.utils.ThreadUtils;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 */
public class DataProvider {

    private final String TAG = DataProvider.class.getSimpleName();

    private static DataProvider mInstance = new DataProvider();
    private static MTProvider mMTProvider;
    private static WikProvider mWikProvider;
    private static DbProvider mDbProvider;

    public static DataProvider getInstance(){
        return mInstance;
    }

    public void init(){
        Log.d(TAG, "init - Register to the EventBus..");
        EventBus.getDefault().register(this);

        mMTProvider = new MTProvider();
        mWikProvider = new WikProvider();
        mDbProvider = new DbProvider();
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onGetListGIEvent(GetListGIEvent event){

        Log.d(TAG, "onGetListGIEvent - isMainThread = " + ThreadUtils.isMainThread());

        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.listAllProducts();

        if (products!=null && products.size() > 0){
            EventBus.getDefault().post(new ReturnListGIReturnEvent(products, false, null));
        }else {
            mMTProvider.searchAllGlycemicIndexes();
        }
    }

    public void destroy(){
        EventBus.getDefault().unregister(this);

        mMTProvider.destroy();
        mWikProvider.destroy();
        mDbProvider.destroy();
    }

}
