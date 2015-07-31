package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fab on 18/06/15.
 */
public class ProductList implements Parcelable {

    public static final Creator<ProductList> CREATOR = new Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel in) {
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };
    private List<Product> mProducts;

    public ProductList(){
        mProducts = new ArrayList<Product>();
    }

    public ProductList(List<Product> products){
        this();
        mProducts.addAll(products);
    }

    protected ProductList(Parcel in) {
        mProducts = in.createTypedArrayList(Product.CREATOR);
    }

    public void add(Product product){
        mProducts.add(product);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mProducts);
    }

    public int size() {
        return mProducts.size();
    }

    public List<Product> getList() {
        return mProducts;
    }
}
