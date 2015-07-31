package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class GlycemicIndexListTest extends TestCase{

    private ProductList sut;

    @Before
    public void setup()  {
        sut = new ProductList();
    }

    @Test
    public void testGIListParcelable(){

        sut.add(new Product("title1","",""));
        sut.add(new Product("title2","",""));
        Parcel parcel = Parcel.obtain();
        sut.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        parcel.setDataPosition(0);

        ProductList list2 = ProductList.CREATOR.createFromParcel(parcel);
        assertEquals(2, list2.size());
    }

}
