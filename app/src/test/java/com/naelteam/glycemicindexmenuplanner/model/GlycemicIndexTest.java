package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class GlycemicIndexTest extends TestCase{

    private GlycemicIndex sut;

    @Before
    public void setup()  {
        sut = new GlycemicIndex("title1","","");
    }

    @Test
    public void testGIParcelable(){
        Parcel parcel = Parcel.obtain();
        sut.writeToParcel(parcel, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        parcel.setDataPosition(0);
        GlycemicIndex glycemicIndex2= GlycemicIndex.CREATOR.createFromParcel(parcel);
        System.out.println("value=" +glycemicIndex2.getTitle());
        assertEquals("title1", glycemicIndex2.getTitle());
    }

    @Test
    public void testEquals(){
        GlycemicIndex glycemicIndex1 = new GlycemicIndex("title1", "","");
        GlycemicIndex glycemicIndex2 = new GlycemicIndex("title1","","");

        List<GlycemicIndex> glycemicIndexList = new ArrayList<GlycemicIndex>();
        glycemicIndexList.add(glycemicIndex1);
        glycemicIndexList.add(glycemicIndex2);

        assertEquals(glycemicIndex1.hashCode(), glycemicIndex2.hashCode());
        assertEquals(true, glycemicIndex1.equals(glycemicIndex2));
        assertEquals(true, glycemicIndexList.contains(glycemicIndex2));
    }

    @Test
    public void testNotEquals(){
        GlycemicIndex glycemicIndex1 = new GlycemicIndex("title1", "","");
        GlycemicIndex glycemicIndex2 = new GlycemicIndex("title2","","");

        assertTrue(glycemicIndex1.hashCode() != glycemicIndex2.hashCode());
        assertEquals(false, glycemicIndex1.equals(glycemicIndex2));
    }
}
