package com.naelteam.glycemicindexmenuplanner.provider.wik;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.network.error.BaseError;
import com.naelteam.glycemicindexmenuplanner.provider.mt.MTProvider;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.SearchGIByNameParser;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public interface Listener{
        void onListGlycemicIndexesSuccess(List<GlycemicIndex> glycemicIndexes);
        void onListGlycemicIndexesError(BaseError error);
    }

public class WikProvider {

    private final static String WIK_SEARCH_GI_BY_NAME_URL = "/w/index.php?title=Special%3ASearch&profile=default&search={search}&fulltext=Search";
    private static final String UK_BASE_URL = "https://en.wikipedia.org";
    private static final String FR_BASE_URL = "https://fr.wikipedia.org";
    private static final String WIK_SEARCH_GI_BY_NAME_TYPE = "WIK_SEARCH_GI_BY_NAME_TYPE";

    private final String TAG = MTProvider.class.getSimpleName();

    public WikProvider() {
        Log.d(TAG, "Register to the EventBus..");
        EventBus.getDefault().register(this);
    }


    public void searchGIByName(final String searchStr, Locale locale) {
        String url = buildURL(WIK_SEARCH_GI_BY_NAME_TYPE, searchStr, locale);

        /*CustomVolleyRequest request = new CustomVolleyRequest(Request.Method.GET, WIK_SEARCH_GI_BY_NAME_URL, new SearchGIByNameParser(), new CustomVolleyRequest.Listener<List<GlycemicIndex>>() {
            @Override
            public void onSuccess(List<GlycemicIndex> glycemicIndexes) {
                Log.d(TAG, "searchGIByName - onSuccess");
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.d(TAG, "searchGIByName - onError");
            }
        });
        VolleyWrapper.getInstance().addRequest(request);*/

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getBaseUrl(locale))
                .build();
        final WikService wikService = restAdapter.create(WikService.class);

        Observable<Response> observable = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = wikService.searchGIByName(searchStr);
                    String data = new String(((TypedByteArray) response.getBody()).getBytes());
                    new SearchGIByNameParser().parse(data);
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
        observable.subscribe(new Observer<Response>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Response response) {

            }
        });

    }

    private String getBaseUrl(Locale locale){
        if (locale.equals(Locale.FRENCH)){
            return FR_BASE_URL;
        }else{
            return UK_BASE_URL;
        }
    }

    private String buildURL(String searchType, String searchStr, Locale locale){
        String url = "";
        String baseUrl = getBaseUrl(locale);

        if (searchType.equals(WIK_SEARCH_GI_BY_NAME_TYPE)){
            url = baseUrl + WIK_SEARCH_GI_BY_NAME_URL;
        }else {
            // manage other types
        }
        return url.replace("?", searchStr);
    }

    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    private interface WikService {
        @GET(WIK_SEARCH_GI_BY_NAME_URL)
        Response searchGIByName(@Path("search") String searchStr);
    }

}
