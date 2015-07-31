package com.naelteam.glycemicindexmenuplanner.event;

import com.naelteam.glycemicindexmenuplanner.model.Product;

import java.util.List;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnListGIReturnEvent extends BaseReturnEvent {

    private List<Product> mProducts;

    public ReturnListGIReturnEvent(List<Product> products, Throwable error){
        super(error);
        mProducts = products;
    }

    public List<Product> getGlycemicIndexes() {
        return mProducts;
    }


}
