package com.naelteam.glycemicindexmenuplanner.provider.wk.parser;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.FileUtils;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.provider.mt.parser.SearchListGIParser;
import com.naelteam.glycemicindexmenuplanner.provider.wik.parser.WikFetchGIDetailsParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class WikFetchGIDetailsParserTest {

    private MainActivity mActivity;
    private WikFetchGIDetailsParser sut;

    public WikFetchGIDetailsParserTest() {
    }

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
        sut = new WikFetchGIDetailsParser();
    }

    private WikProduct parseHtml(String htmlPage){
        try {
            String data = FileUtils.loadAssetFile(mActivity.getApplicationContext(), htmlPage);
            return sut.parse(data);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    public void testValidAlmondParser(){
        WikProduct wikProduct = parseHtml("wk_fetch_almond_gi_details.html");
        Assert.assertTrue(wikProduct !=null);
    }


    @Test
    public void testValidAlcoholParser(){
        WikProduct wikProduct = parseHtml("wk_fetch_alcohol_gi_details.html");
        Assert.assertTrue(wikProduct !=null);
    }

}