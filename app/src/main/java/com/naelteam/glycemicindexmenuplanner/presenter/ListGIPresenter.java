package com.naelteam.glycemicindexmenuplanner.presenter;

import android.os.Parcelable;
import android.util.Log;

import com.naelteam.glycemicindexmenuplanner.dao.ProductDao;
import com.naelteam.glycemicindexmenuplanner.event.GetListGIEvent;
import com.naelteam.glycemicindexmenuplanner.event.ReturnListGIReturnEvent;
import com.naelteam.glycemicindexmenuplanner.event.UISearchGIEvent;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductGroup;
import com.naelteam.glycemicindexmenuplanner.model.ProductList;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 */
public class ListGIPresenter {

    private final static String TAG = ListGIPresenter.class.getSimpleName();

    private Listener mListener;

    private ProductList mProductList;

    public ListGIPresenter(Listener listener){
        mListener = listener;
    }

    public List<IGlycemicIndex> getInitialDatas(){
        List<IGlycemicIndex> datas = new ArrayList<IGlycemicIndex>();
        for (int i = 65; i < 91; i++) {
            datas.add(new ProductGroup(Character.toChars(i)[0] + ""));
        }
        return datas;
    }

    public Parcelable getDataList(){
        return mProductList;
    }

    public void setDataList(Parcelable parcelable){
        mProductList = (ProductList) parcelable;
    }

    public int getDataCount(){
        if (mProductList !=null){
            return mProductList.size();
        }
        return 0;
    }

    public List<IGlycemicIndex> getData(String filter){
        List<IGlycemicIndex> datas = new ArrayList<IGlycemicIndex>();
        for (Product product : mProductList.getList()){
            if (product.getTitle().toLowerCase().startsWith(filter.toLowerCase())){
                datas.add(product);
            }
        }
        return datas;
    }

    public void listGlycementIndexes() {
        EventBus.getDefault().post(new GetListGIEvent());
    }

    @Subscribe
    public void onReturnListGIReturnEvent(ReturnListGIReturnEvent event){
        if (event.getError()!= null){
            Log.d(TAG, "onReturnListGIReturnEvent - Error " + event.getError());
            mListener.onListGIError();
        }else {
            Log.d(TAG, "onReturnListGIReturnEvent - Success");
            mProductList = new ProductList(event.getProducts());
            mListener.onListGISuccess();
        }
    }

   @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onReturnListGIReturnEventBackground(ReturnListGIReturnEvent event){
        if (event.getError()== null){
            Log.d(TAG, "onReturnListGIReturnEventBackground - Success");
            if (event.needPersistData()) {
                ProductDao dao = new ProductDao();
                dao.insertProducts(event.getProducts());
            }
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

    public void start() {
        EventBus.getDefault().register(this);
    }

    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    public interface Listener{
        void onListGISuccess();
        void onListGIError();
        void onSearchGI(List<IGlycemicIndex> glycemicIndexes);
    }
}
