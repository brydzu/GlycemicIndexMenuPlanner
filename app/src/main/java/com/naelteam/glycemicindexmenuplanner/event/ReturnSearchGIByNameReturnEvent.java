package com.naelteam.glycemicindexmenuplanner.event;

import com.naelteam.glycemicindexmenuplanner.model.WikProduct;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnSearchGIByNameReturnEvent extends BaseReturnEvent {

    private WikProduct mWikProduct;

    public ReturnSearchGIByNameReturnEvent(WikProduct wikProduct, Throwable error){
        super(error);
        mWikProduct = wikProduct;
    }

    public WikProduct getWikProduct() {
        return mWikProduct;
    }

}
