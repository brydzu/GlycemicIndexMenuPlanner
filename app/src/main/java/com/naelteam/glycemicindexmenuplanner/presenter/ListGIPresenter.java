package com.naelteam.glycemicindexmenuplanner.presenter;

import android.os.Parcelable;
import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.event.GetListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.ReturnListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.UISearchGIEvent;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexList;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by fab on 07/06/15.
 */
public class ListGIPresenter {

    private final static String TAG = ListGIPresenter.class.getSimpleName();

    private Listener mListener;

    private GlycemicIndexList mGlycemicIndexList;

    public ListGIPresenter(Listener listener){
        mListener = listener;
    }

    public List<IGlycemicIndex> getInitialDatas(){
        List<IGlycemicIndex> datas = new ArrayList<IGlycemicIndex>();
        for (int i = 65; i < 91; i++) {
            datas.add(new GlycemicIndexGroup(Character.toChars(i)[0] + ""));
        }
        return datas;
    }

    public Parcelable getDataList(){
        return mGlycemicIndexList;
    }

    public void setDataList(Parcelable parcelable){
        mGlycemicIndexList = (GlycemicIndexList) parcelable;
    }

    public int getDataCount(){
        if (mGlycemicIndexList!=null){
            return mGlycemicIndexList.size();
        }
        return 0;
    }

    public List<IGlycemicIndex> getData(String filter){
        List<IGlycemicIndex> datas = new ArrayList<IGlycemicIndex>();
        for (GlycemicIndex glycemicIndex:mGlycemicIndexList.getList()){
            if (glycemicIndex.getTitle().toLowerCase().startsWith(filter.toLowerCase())){
                datas.add(glycemicIndex);
            }
        }
        return datas;
    }

    public void listGlycementIndexes() {
        EventBus.getDefault().post(new GetListGIEvent());

        /*DataProvider.getMTProvider().listGlycemicIndexes(new MTProvider.Listener() {
            @Override
            public void onListGlycemicIndexesSuccess(List<GlycemicIndex> glycemicIndexes) {
            }
            @Override
            public void onListGlycemicIndexesError(BaseError error) {
            }
        });*/
    }

    @Subscribe
    public void onEvent(ReturnListGIEvent event){
        if (event.getError()!= null){
            Log.d(TAG, "onEvent - Error " + event.getError());
            mListener.onListGIError();
        }else {
            Log.d(TAG, "onEvent - Success");
            mGlycemicIndexList = new GlycemicIndexList(event.getGlycemicIndexes());
            mListener.onListGISuccess();
        }
    }

    @Subscribe
    public void onEvent(UISearchGIEvent event){
        Log.d(TAG, "onEvent - UISearchGIEvent ");

        final String searchTxt = event.getSearchText();
        if (searchTxt!= null){
            List<IGlycemicIndex> glycemicIndexes  = getData(searchTxt.toLowerCase());
            mListener.onSearchGI(glycemicIndexes);
        }
    }

    public void resume() {
        EventBus.getDefault().register(this);
    }

    public void pause() {
        EventBus.getDefault().unregister(this);
    }

    public interface Listener{
        void onListGISuccess();
        void onListGIError();
        void onSearchGI(List<IGlycemicIndex> glycemicIndexes);
    }
}
