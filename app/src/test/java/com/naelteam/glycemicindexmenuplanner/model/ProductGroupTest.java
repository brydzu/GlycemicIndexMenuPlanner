package com.naelteam.glycemicindexmenuplanner.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ProductGroupTest extends TestCase{

    public static final String TITLE_P = "titleP";
    private ProductGroup sut;

    @Override
    public void setUp()  {
        sut = new ProductGroup(TITLE_P);
    }

    public void testEquals(){
        ProductGroup glycemicIndex2 = new ProductGroup(TITLE_P);

        List<IGlycemicIndex> glycemicIndexList = new ArrayList<IGlycemicIndex>();
        glycemicIndexList.add(sut);
        glycemicIndexList.add(glycemicIndex2);

        assertEquals(sut.hashCode(), glycemicIndex2.hashCode());
        assertEquals(true, sut.equals(glycemicIndex2));
        assertEquals(true, glycemicIndexList.contains(glycemicIndex2));
    }

    public void testNotEquals(){
        ProductGroup glycemicIndex2 = new ProductGroup("title2");

        assertTrue(sut.hashCode() != glycemicIndex2.hashCode());
        assertEquals(false, sut.equals(glycemicIndex2));
    }
}
