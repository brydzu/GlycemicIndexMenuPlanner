package com.naelteam.glycemicindexmenuplanner.provider.wk.parser;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikFetchGIDetailsParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 */
public class WikFetchGIDetailsParserTest extends BaseTest{

    private WikFetchGIDetailsParser sut;

    public WikFetchGIDetailsParserTest() {
    }

    @Before
    public void setup()  {
        super.setup();
        sut = new WikFetchGIDetailsParser();
    }

    private Product parseHtml(String htmlPage){
        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), htmlPage);
            return sut.parse(data);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void testValidAlmondParser(){
        Product wikProduct = parseHtml("wk_fetch_almond_gi_details.html");
        Assert.assertTrue(wikProduct !=null);
    }


    @Test
    public void testValidAlcoholParser(){
        Product wikProduct = parseHtml("wk_fetch_alcohol_gi_details.html");
        Assert.assertTrue(wikProduct !=null);
    }

}