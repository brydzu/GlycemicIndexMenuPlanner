package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.BuildConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

public class ProductListTest extends BaseTest{

    private ProductList sut;

    @Before
    public void setUp()  {
        sut = new ProductList();
    }

    @Test
    public void testParcelable(){

        sut.add(new Product("title1","",""));
        sut.add(new Product("title2","",""));
        Parcel parcel = Parcel.obtain();
        sut.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        parcel.setDataPosition(0);

        ProductList list2 = ProductList.CREATOR.createFromParcel(parcel);
        Assert.assertEquals(2, list2.size());
    }

}
