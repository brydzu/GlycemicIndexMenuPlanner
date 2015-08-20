package com.naelteam.glycemicindexmenuplanner.adapter;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.model.Section;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DetailsRecyclerAdapterTest extends BaseTest{

    private DetailsGIRecyclerAdapter sut;

    @Before
    public void setup()  {
        super.setup();
        sut = new DetailsGIRecyclerAdapter(mActivity);
        sut.add(new Section("title1","description1"));
        sut.add(new Section("title2",null));
    }

    @Test
    public void testGetItemCount(){
        Assert.assertEquals(2, sut.getItemCount());
    }

    @Test
    public void testGetItemViewType(){
        Assert.assertEquals(DetailsGIRecyclerAdapter.SECTION_CONTENT_VIEW_TYPE, sut.getItemViewType(0));
        Assert.assertEquals(DetailsGIRecyclerAdapter.SECTION_VIEW_TYPE, sut.getItemViewType(1));
    }
}
