package com.naelteam.glycemicindexmenuplanner.presenter;

/**
 * Created by fab on 07/06/15.
 */
public class DetailsGIPresenter {

    private final static String TAG = DetailsGIPresenter.class.getSimpleName();

    private Listener mListener;

    public DetailsGIPresenter(Listener listener){
        mListener = listener;
    }

    public void searchDetailsOnGlycemicIndex() {
        //EventBus.getDefault().post(new GetListGIEvent());
    }

    public void resume() {
        //EventBus.getDefault().register(this);
    }

    public void pause() {
        //EventBus.getDefault().unregister(this);
    }

    public interface Listener{
        void onSearchDetailGISuccess();
        void onSearchDetailGIError();
    }
}
