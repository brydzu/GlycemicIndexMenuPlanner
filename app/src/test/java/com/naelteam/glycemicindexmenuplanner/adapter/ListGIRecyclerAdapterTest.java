package com.naelteam.glycemicindexmenuplanner.adapter;

import android.app.Activity;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductGroup;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ListGIRecyclerAdapterTest extends TestCase{

    private ListGIRecyclerAdapter sut;
    private Activity mActivity;

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        sut = new ListGIRecyclerAdapter(mActivity);
    }

    @Test
    public void testGetItemCount(){
        sut.add(new Product("title1","",""));
        sut.add(new Product("title2","",""));
        assertEquals(2, sut.getItemCount());
    }


    @Test
    public void testGetItemViewType(){
        ProductGroup productGroup = new ProductGroup("title1");
        sut.add(productGroup);
        Product product1 = new Product("title2","","");
        sut.add(product1);
        Product product2 = new Product("title3","","");
        sut.add(product2);
        assertEquals(ListGIRecyclerAdapter.GROUP_VIEW_TYPE, sut.getItemViewType(0));
        assertEquals(ListGIRecyclerAdapter.ITEM_VIEW_TYPE, sut.getItemViewType(1));
        assertEquals(ListGIRecyclerAdapter.ITEM_VIEW_TYPE, sut.getItemViewType(2));
    }
}
