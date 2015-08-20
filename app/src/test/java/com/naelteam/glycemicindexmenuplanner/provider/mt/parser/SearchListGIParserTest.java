package com.naelteam.glycemicindexmenuplanner.provider.mt.parser;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.model.Product;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 */
public class SearchListGIParserTest extends BaseTest{

    private SearchListGIParser sut;

    public SearchListGIParserTest() {
    }

    @Before
    public void setup()  {
        super.setup();
        sut = new SearchListGIParser();
    }

    @Test
    public void testParser(){

        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), "mt_list_gi.html");
            List<Product> products = sut.parse(data);
            Assert.assertTrue(products !=null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}