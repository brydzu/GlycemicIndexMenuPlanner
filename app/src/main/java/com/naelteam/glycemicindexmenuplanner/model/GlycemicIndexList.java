package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fab on 18/06/15.
 */
public class GlycemicIndexList implements Parcelable {

    public static final Creator<GlycemicIndexList> CREATOR = new Creator<GlycemicIndexList>() {
        @Override
        public GlycemicIndexList createFromParcel(Parcel in) {
            return new GlycemicIndexList(in);
        }

        @Override
        public GlycemicIndexList[] newArray(int size) {
            return new GlycemicIndexList[size];
        }
    };
    private List<GlycemicIndex> mGlycemicIndexes;

    public GlycemicIndexList(){
        mGlycemicIndexes = new ArrayList<GlycemicIndex>();
    }

    public GlycemicIndexList(List<GlycemicIndex> glycemicIndexes){
        this();
        mGlycemicIndexes.addAll(glycemicIndexes);
    }

    protected GlycemicIndexList(Parcel in) {
        mGlycemicIndexes = in.createTypedArrayList(GlycemicIndex.CREATOR);
    }

    public void add(GlycemicIndex glycemicIndex){
        mGlycemicIndexes.add(glycemicIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mGlycemicIndexes);
    }

    public int size() {
        return mGlycemicIndexes.size();
    }

    public List<GlycemicIndex> getList() {
        return mGlycemicIndexes;
    }
}
