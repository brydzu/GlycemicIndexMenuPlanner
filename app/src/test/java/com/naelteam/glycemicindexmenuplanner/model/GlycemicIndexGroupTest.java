package com.naelteam.glycemicindexmenuplanner.model;

import com.naelteam.glycemicindexmenuplanner.BuildConfig;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class GlycemicIndexGroupTest extends TestCase{

    @Before
    public void setup()  {
    }

    @Test
    public void testEquals(){
        GlycemicIndexGroup glycemicIndex1 = new GlycemicIndexGroup("title1");
        GlycemicIndexGroup glycemicIndex2 = new GlycemicIndexGroup("title1");

        List<IGlycemicIndex> glycemicIndexList = new ArrayList<IGlycemicIndex>();
        glycemicIndexList.add(glycemicIndex1);
        glycemicIndexList.add(glycemicIndex2);

        assertEquals(glycemicIndex1.hashCode(), glycemicIndex2.hashCode());
        assertEquals(true, glycemicIndex1.equals(glycemicIndex2));
        assertEquals(true, glycemicIndexList.contains(glycemicIndex2));
    }

    @Test
    public void testNotEquals(){
        GlycemicIndexGroup glycemicIndex1 = new GlycemicIndexGroup("title1");
        GlycemicIndexGroup glycemicIndex2 = new GlycemicIndexGroup("title2");

        assertTrue(glycemicIndex1.hashCode() != glycemicIndex2.hashCode());
        assertEquals(false, glycemicIndex1.equals(glycemicIndex2));
    }
}
