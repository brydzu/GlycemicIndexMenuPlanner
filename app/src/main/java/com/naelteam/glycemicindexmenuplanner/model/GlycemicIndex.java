package com.naelteam.glycemicindexmenuplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fab on 02/06/15.
 */
public class GlycemicIndex implements Parcelable, IGlycemicIndex{

    public static final Creator<GlycemicIndex> CREATOR = new Creator<GlycemicIndex>() {
        @Override
        public GlycemicIndex createFromParcel(Parcel in) {
            return new GlycemicIndex(in);
        }

        @Override
        public GlycemicIndex[] newArray(int size) {
            return new GlycemicIndex[size];
        }
    };
    private String mTitle;
    private String mDescription;
    private String mValue;
    private boolean mSelected;

    public GlycemicIndex(String title, String description, String value){
        mTitle = title;
        mDescription = description;
        mValue = value;
    }

    protected GlycemicIndex(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mValue = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public boolean isSelected(){
        return mSelected;
    }

    public void setSelected(boolean selected){
        mSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mValue);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
        result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
        result = prime * result + ((mValue == null) ? 0 : mValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GlycemicIndex other = (GlycemicIndex) obj;
        if (mTitle == null) {
            if (other.mTitle != null)
                return false;
        } else if (!mTitle.equals(other.mTitle))
            return false;
        if (mDescription == null) {
            if (other.mDescription != null)
                return false;
        } else if (!mDescription.equals(other.mDescription))
            return false;
        if (mValue == null) {
            if (other.mValue != null)
                return false;
        } else if (!mValue.equals(other.mValue))
            return false;
        return true;
    }
}
