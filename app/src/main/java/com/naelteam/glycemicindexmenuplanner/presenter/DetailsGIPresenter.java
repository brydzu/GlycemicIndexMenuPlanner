package com.naelteam.glycemicindexmenuplanner.presenter;

import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.event.ReturnSearchGIByNameReturnEvent;
import com.naelteam.glycemicindexmenuplanner.event.SearchGIByNameEvent;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by fab on 07/06/15.
 */
public class DetailsGIPresenter {

    private final static String TAG = DetailsGIPresenter.class.getSimpleName();

    private Listener mListener;

    public DetailsGIPresenter(Listener listener){
        mListener = listener;
    }

    public void searchDetailsOnGlycemicIndex(String giName) {
        EventBus.getDefault().post(new SearchGIByNameEvent(giName));
    }

    @Subscribe
    public void onEventMainThread(ReturnSearchGIByNameReturnEvent event){
        if (event.getError()!= null){
            Log.d(TAG, "onEvent - ReturnSearchGIByNameReturnEvent, Error " + event.getError());
            mListener.onSearchDetailGIError();
        }else {
            Log.d(TAG, "onEvent - ReturnSearchGIByNameReturnEvent, Success");
            mListener.onSearchDetailGISuccess(event.getWikProduct());
        }
    }

    public void resume() {
        EventBus.getDefault().register(this);
    }

    public void pause() {
        EventBus.getDefault().unregister(this);
    }

    public interface Listener{
        void onSearchDetailGISuccess(WikProduct wikProduct);
        void onSearchDetailGIError();
    }
}