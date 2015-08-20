package com.naelteam.glycemicindexmenuplanner;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml", application = CustomApplicationTest.class)
public class BaseTest {

    protected Activity mActivity;

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void initTest(){

    }
}
