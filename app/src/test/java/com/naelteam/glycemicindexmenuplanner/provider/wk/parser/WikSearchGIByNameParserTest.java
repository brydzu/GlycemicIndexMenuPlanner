package com.naelteam.glycemicindexmenuplanner.provider.wk.parser;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikSearchGIByNameParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class WikSearchGIByNameParserTest {

    private MainActivity mActivity;
    private WikSearchGIByNameParser sut;

    public WikSearchGIByNameParserTest() {
    }

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
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