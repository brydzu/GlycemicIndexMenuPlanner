package com.naelteam.glycemicindexmenuplanner.provider.mt.parser;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class SearchGIByProdNameParserTest {

    private MainActivity mActivity;
    private SearchGIByProdNameParser sut;

    public SearchGIByProdNameParserTest() {
    }

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
        sut = new SearchGIByProdNameParser();
    }

    @Test
    public void testParser(){

        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), "mt_search_gi_by_name.html");
            List<GlycemicIndex> glycemicIndexes = sut.parse(data);
            Assert.assertTrue(glycemicIndexes !=null);
        } catch (IOException e) {
            throw new AssertionError(e);
        }

    }


}