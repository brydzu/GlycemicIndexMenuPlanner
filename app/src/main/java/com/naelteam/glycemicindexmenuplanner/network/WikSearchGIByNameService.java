package com.naelteam.glycemicindexmenuplanner.network;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikFetchGIDetailsParser;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikSearchGIByNameParser;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fab on 04/07/15.
 */
public class WikSearchGIByNameService {

    private final static String TAG = WikSearchGIByNameService.class.getSimpleName();

    public final static String WIK_SEARCH_GI_BY_NAME_URL = "/w/index.php?title=Special%3ASearch&profile=default&fulltext=Search";
    public final static String WIK_FETCH_GI_DETAILS = "/wiki/{product}";

    private final WikService mWikService;

    private interface WikService {
        @GET(WIK_SEARCH_GI_BY_NAME_URL)
        Observable<Response> searchGIByName(@Query("search") String searchStr);

        @GET(WIK_FETCH_GI_DETAILS)
        Observable<Response> fetchGIDetails(@Path("product") String productUrl);
    }

    public WikSearchGIByNameService(String baseUrl){
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .build();
        mWikService = restAdapter.create(WikService.class);
    }

    public Observable<String> requestAndParseObs(String searchStr){

        final Observable<Response> searchGIByNameObs = mWikService.searchGIByName(searchStr);

        return searchGIByNameObs.flatMap(new Func1<Response, Observable<String>>() {
            @Override
            public Observable<String> call(Response response) {
                Log.d(TAG, "requestAndParseObs - call");
                String data = new String(((TypedByteArray) response.getBody()).getBytes());
                String productUrl = new WikSearchGIByNameParser().parse(data);
                return Observable.just(productUrl);
            }
        });
    }

    public Observable<WikProduct> getProductDetails(String searchStr){
        Observable<Response> responseObservable = requestAndParseObs(searchStr).flatMap(new Func1<String, Observable<Response>>() {
            @Override
            public Observable<Response> call(String productUrl) {
                Log.d(TAG, "getProductDetails - call");
                return mWikService.fetchGIDetails(productUrl.substring(productUrl.lastIndexOf("/") + 1));
            }
        });
        return responseObservable.flatMap(new Func1<Response, Observable<WikProduct>>() {
            @Override
            public Observable<WikProduct> call(Response response) {
                Log.d(TAG, "getProductDetails - call");
                String data = new String(((TypedByteArray) response.getBody()).getBytes());
                WikProduct wikProduct = new WikFetchGIDetailsParser().parse(data);
                return Observable.just(wikProduct);
            }
        });
    }
}
