package com.naelteam.glycemicindexmenuplanner.network;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikFetchGIDetailsParser;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikSearchGIByNameParser;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
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

        @Headers({
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36"
        })
        @GET(WIK_FETCH_GI_DETAILS)
        Observable<Response> fetchGIDetails(@Path("product") String productUrl);
    }

    //user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36

    public WikSearchGIByNameService(String baseUrl){
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl)
                .build();
        mWikService = restAdapter.create(WikService.class);
    }

    public Observable<String> requestAndParseObs(String searchStr){

        //filter URL
        String searchUrl = searchStr.replaceAll("\**", "");

        final Observable<Response> searchGIByNameObs = mWikService.searchGIByName(searchUrl);

        return searchGIByNameObs.flatMap(new Func1<Response, Observable<String>>() {
            @Override
            public Observable<String> call(Response response) {
                Log.d(TAG, "searchGIByName - requestAndParseObs, url = '" + response.getUrl() + "', status = " + response.getStatus());
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
                Log.d(TAG, "getProductDetails - call, url = " + productUrl);
                return mWikService.fetchGIDetails(productUrl.substring(productUrl.lastIndexOf("/") + 1));
            }
        });
        return responseObservable.flatMap(new Func1<Response, Observable<WikProduct>>() {
            @Override
            public Observable<WikProduct> call(Response response) {
                Log.d(TAG, "getProductDetails - response");
                String data = new String(((TypedByteArray) response.getBody()).getBytes());
                WikProduct wikProduct = new WikFetchGIDetailsParser().parse(data);
                return Observable.just(wikProduct);
            }
        });
    }
}
