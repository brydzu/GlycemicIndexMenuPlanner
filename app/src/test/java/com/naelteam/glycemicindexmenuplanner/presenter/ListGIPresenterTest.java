package com.naelteam.glycemicindexmenuplanner.presenter;

import android.app.Activity;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;
import com.naelteam.glycemicindexmenuplanner.MainActivity;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class ListGIPresenterTest extends TestCase{

    private ListGIPresenter sut;
    private Activity mActivity;

    @Before
    public void setup()  {
        mActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        sut = new ListGIPresenter(null);
    }

    @Test
    public void testGetInitialDatas(){
        List<IGlycemicIndex> glycemicIndexes = sut.getInitialDatas();
        assertTrue(glycemicIndexes != null);
        assertTrue(glycemicIndexes.size()!=0);

        final IGlycemicIndex glycemicIndex = glycemicIndexes.get(0);
        assertTrue(glycemicIndex instanceof GlycemicIndexGroup);
        assertEquals("A", glycemicIndex.getTitle());

    }
}
