package com.naelteam.glycemicindexmenuplanner.event;


import com.naelteam.glycemicindexmenuplanner.model.Product;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnSearchGIByNameReturnEvent extends BaseReturnEvent {

    private Product product;

    public ReturnSearchGIByNameReturnEvent(Product product, Throwable error){
        super(error);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}
