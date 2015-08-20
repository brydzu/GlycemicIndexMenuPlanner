package com.naelteam.glycemicindexmenuplanner.adapter;

import android.app.Activity;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductGroup;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

public class ListGIRecyclerAdapterTest extends BaseTest{

    private ListGIRecyclerAdapter sut;

    @Before
    public void setup()  {
        super.setup();
        sut = new ListGIRecyclerAdapter(mActivity);
        sut.add(new Product("title1","",""));
        sut.add(new Product("title2","",""));
    }

    @Test
    public void testGetItemCount(){
        Assert.assertEquals(2, sut.getItemCount());
    }


    @Test
    public void testGetItemViewType(){
        ProductGroup productGroup = new ProductGroup("title1");
        sut.add(productGroup);
        Assert.assertEquals(ListGIRecyclerAdapter.ITEM_VIEW_TYPE, sut.getItemViewType(0));
        Assert.assertEquals(ListGIRecyclerAdapter.ITEM_VIEW_TYPE, sut.getItemViewType(1));
        Assert.assertEquals(ListGIRecyclerAdapter.GROUP_VIEW_TYPE, sut.getItemViewType(2));
    }
}
