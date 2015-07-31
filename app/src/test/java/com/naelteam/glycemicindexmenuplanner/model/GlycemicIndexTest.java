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

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class GlycemicIndexTest extends TestCase{

    private Product sut;

    @Before
    public void setup()  {
        sut = new Product("title1","","");
    }

    @Test
    public void testGIParcelable(){
        Parcel parcel = Parcel.obtain();
        sut.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        parcel.setDataPosition(0);
        Product product2 = Product.CREATOR.createFromParcel(parcel);
        System.out.println("value=" + product2.getTitle());
        assertEquals("title1", product2.getTitle());
    }

    @Test
    public void testEquals(){
        Product product1 = new Product("title1", "","");
        Product product2 = new Product("title1","","");

        List<Product> productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        assertEquals(product1.hashCode(), product2.hashCode());
        assertEquals(true, product1.equals(product2));
        assertEquals(true, productList.contains(product2));
    }

    @Test
    public void testNotEquals(){
        Product product1 = new Product("title1", "","");
        Product product2 = new Product("title2","","");

        assertTrue(product1.hashCode() != product2.hashCode());
        assertEquals(false, product1.equals(product2));
    }
}
