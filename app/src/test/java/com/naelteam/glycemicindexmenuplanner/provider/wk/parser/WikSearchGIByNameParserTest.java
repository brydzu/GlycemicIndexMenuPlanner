package com.naelteam.glycemicindexmenuplanner.provider.wk.parser;

import com.naelteam.glycemicindexmenuplanner.BaseTest;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikSearchGIByNameParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.io.IOException;

/**
 */
public class WikSearchGIByNameParserTest extends BaseTest {

    private WikSearchGIByNameParser sut;

    public WikSearchGIByNameParserTest() {
    }

    @Before
    public void setup()  {
        super.setup();
        sut = new WikSearchGIByNameParser();
    }

    @Test
    public void testParserIfHasResult(){

        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), "wk_search_gi_by_name.html");
            String productUrl = sut.parse(data);
            Assert.assertTrue(productUrl != null);
        } catch (IOException e) {
            throw new AssertionError(e);
        }

    }

    @Test
    public void testParserIfNoResult(){

        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), "wk_search_error_gi_by_name.html");
            String productUrl = sut.parse(data);
            Assert.assertTrue(productUrl == null);
        } catch (IOException e) {
            throw new AssertionError(e);
        }

    }
}