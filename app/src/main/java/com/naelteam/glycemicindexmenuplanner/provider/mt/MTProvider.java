package com.naelteam.glycemicindexmenuplanner.provider.mt;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.naelteam.glycemicindexmenuplanner.event.GetListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.ReturnListGIReturnEvent;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.network.CustomVolleyRequest;
import com.naelteam.glycemicindexmenuplanner.network.VolleyWrapper;
import com.naelteam.glycemicindexmenuplanner.network.error.BaseError;
import com.naelteam.glycemicindexmenuplanner.provider.mt.parser.SearchGIByProdNameParser;
import com.naelteam.glycemicindexmenuplanner.provider.mt.parser.SearchListGIParser;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 */
public class MTProvider {

    private final static String MT_LIST_GI_URL = "http://www.montignac.com/en/search-for-a-specific-glycemic-index/";
    private final static String MT_SEARCH_BY_PROD_NAME_URL = "http://www.montignac.com/modules/glycemic_index/front/ajax/search.php";

    private final String TAG = MTProvider.class.getSimpleName();

    public MTProvider() {
        //Log.d(TAG, "MTProvider - Register to the EventBus..");
        //EventBus.getDefault().register(this);
    }

    public void destroy() {
        //EventBus.getDefault().unregister(this);
    }

    public void searchGlycemicIndexesByProductName(String searchStr, Language language) {
        CustomVolleyRequest request = new CustomVolleyRequest(Request.Method.POST, MT_SEARCH_BY_PROD_NAME_URL, new SearchGIByProdNameParser(), new CustomVolleyRequest.Listener<List<Product>>() {
            @Override
            public void onSuccess(List<Product> glycemicIndexes) {
                Log.d(TAG, "searchGlycemicIndexes - onSuccess");
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d(TAG, "searchGlycemicIndexes - onError");
            }
        });
        VolleyWrapper.getInstance().addRequest(request);
    }

    public void searchAllGlycemicIndexes(){

       CustomVolleyRequest request = new CustomVolleyRequest(Request.Method.GET, MT_LIST_GI_URL, new SearchListGIParser(), new CustomVolleyRequest.Listener<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                Log.d(TAG, "listGlycemicIndexes - onSuccess, fetch " + products.size() + " products from network");
                EventBus.getDefault().post(new ReturnListGIReturnEvent(products, true, null));
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d(TAG, "listGlycemicIndexes - onError");
                EventBus.getDefault().post(new ReturnListGIReturnEvent(null, false, volleyError));
            }
        });
        VolleyWrapper.getInstance().addRequest(request);

    }

    public enum Language {
        FR("2"), EN("1");

        private String code;
        Language(String code){
            this.code = code;
        }
    }

    public interface Listener{
        void onListGlycemicIndexesSuccess(List<Product> products);
        void onListGlycemicIndexesError(BaseError error);
    }



}
