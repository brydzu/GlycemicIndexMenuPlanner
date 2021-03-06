package com.naelteam.glycemicindexmenuplanner.event;

import com.naelteam.glycemicindexmenuplanner.model.Product;

import java.util.List;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnListGIReturnEvent extends BaseReturnEvent {

    private boolean persistData;
    private List<Product> mProducts;

    public ReturnListGIReturnEvent(List<Product> products, boolean persistData, Throwable error){
        super(error);
        mProducts = products;
        this.persistData = persistData;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public boolean needPersistData(){
        return persistData;
    }

}
