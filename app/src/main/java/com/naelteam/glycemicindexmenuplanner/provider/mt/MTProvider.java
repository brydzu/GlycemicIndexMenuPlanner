package com.naelteam.glycemicindexmenuplanner.provider.mt;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.naelteam.glycemicindexmenuplanner.event.GetListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.ReturnListGIEvent;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.network.CustomVolleyRequest;
import com.naelteam.glycemicindexmenuplanner.network.VolleyUtils;
import com.naelteam.glycemicindexmenuplanner.network.VolleyWrapper;
import com.naelteam.glycemicindexmenuplanner.network.error.BaseError;
import com.naelteam.glycemicindexmenuplanner.provider.mt.parser.SearchGIByProdNameParser;
import com.naelteam.glycemicindexmenuplanner.provider.mt.parser.SearchListGIParser;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by fab on 31/05/15.
 */
public class MTProvider {

    private final static String MT_LIST_GI_URL = "http://www.montignac.com/en/search-for-a-specific-glycemic-index/";
    private final static String MT_SEARCH_BY_PROD_NAME_URL = "http://www.montignac.com/modules/glycemic_index/front/ajax/search.php";

    private final String TAG = MTProvider.class.getSimpleName();

    public MTProvider() {
        Log.d(TAG, "MTProvider - Register to the EventBus..");
        EventBus.getDefault().register(this);
    }

    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    public void searchGlycemicIndexesByProductName(String searchStr, Language language) {
        CustomVolleyRequest request = new CustomVolleyRequest(Request.Method.POST, MT_SEARCH_BY_PROD_NAME_URL, new SearchGIByProdNameParser(), new CustomVolleyRequest.Listener<List<GlycemicIndex>>() {
            @Override
            public void onSuccess(List<GlycemicIndex> glycemicIndexes) {
                Log.d(TAG, "searchGlycemicIndexes - onSuccess");
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d(TAG, "searchGlycemicIndexes - onError");
            }
        });
        VolleyWrapper.getInstance().addRequest(request);

    }

    @Subscribe
    public void onEvent(GetListGIEvent event){
       CustomVolleyRequest request = new CustomVolleyRequest(Request.Method.GET, MT_LIST_GI_URL, new SearchListGIParser(), new CustomVolleyRequest.Listener<List<GlycemicIndex>>() {
            @Override
            public void onSuccess(List<GlycemicIndex> glycemicIndexes) {
                Log.d(TAG, "listGlycemicIndexes - onSuccess");
                EventBus.getDefault().post(new ReturnListGIEvent(glycemicIndexes, null));
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d(TAG, "listGlycemicIndexes - onError");
                EventBus.getDefault().post(new ReturnListGIEvent(null, VolleyUtils.getError(volleyError)));
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
        void onListGlycemicIndexesSuccess(List<GlycemicIndex> glycemicIndexes);
        void onListGlycemicIndexesError(BaseError error);
    }



}