package com.naelteam.glycemicindexmenuplanner.provider.wik;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.event.ReturnSearchGIByNameReturnEvent;
import com.naelteam.glycemicindexmenuplanner.event.SearchGIByNameEvent;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.network.WikSearchGIByNameService;
import com.naelteam.glycemicindexmenuplanner.provider.mt.MTProvider;

import java.util.Locale;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import rx.Observable;
import rx.functions.Action1;

public class WikProvider {


    private static final String UK_BASE_URL = "https://en.wikipedia.org";
    private static final String FR_BASE_URL = "https://fr.wikipedia.org";
    public static final String WIK_SEARCH_GI_BY_NAME_TYPE = "WIK_SEARCH_GI_BY_NAME_TYPE";

    private final String TAG = MTProvider.class.getSimpleName();

    public WikProvider() {
        Log.d(TAG, "Register to the EventBus..");
       EventBus.getDefault().register(this);
    }


    @Subscribe
    public void onEvent(SearchGIByNameEvent event) {

        WikSearchGIByNameService service = new WikSearchGIByNameService(getBaseUrl(Locale.ENGLISH));

        Observable<WikProduct> observable = service.getProductDetails(event.getSearchStr());
        observable.subscribe(new Action1<WikProduct>() {
            @Override
            public void call(WikProduct wikProduct) {
                EventBus.getDefault().post(new ReturnSearchGIByNameReturnEvent(wikProduct, null));
            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable error) {
                EventBus.getDefault().post(new ReturnSearchGIByNameReturnEvent(null, error));
            }
        });

    }

    public static String getBaseUrl(Locale locale){
        if (locale.equals(Locale.FRENCH)){
            return FR_BASE_URL;
        }else{
            return UK_BASE_URL;
        }
    }

    public void destroy() {
        EventBus.getDefault().unregister(this);
    }



}
