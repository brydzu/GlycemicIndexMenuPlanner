package com.naelteam.glycemicindexmenuplanner.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fab on 12/07/15.
 */
public class WikSection {

    private String mTitle;
    private List<WikSection> mSections;
    private String mDescription;
    private String[] mImagesUrl;

    public WikSection(String title){
        this.mTitle = title;
    }

    public WikSection() {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<WikSection> getSections() {
        return mSections;
    }

    public void addSection(WikSection wikSection) {
        if (mSections == null) {
            mSections = new ArrayList<WikSection>();
        }
        this.mSections.add(wikSection);
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String[] getImagesUrl() {
        return mImagesUrl;
    }

    public void setImagesUrl(String[] imagesUrl) {
        this.mImagesUrl = imagesUrl;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
        result = prime * result + ((mDescription == null) ? 0 : mDescription.hashCode());
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
        WikSection other = (WikSection) obj;
        if (mTitle == null) {
            if (other.getTitle() != null)
                return false;
        } else if (!mTitle.equals(other.getTitle()))
            return false;
        if (mDescription == null) {
            if (other.getDescription() != null)
                return false;
        } else if (!mDescription.equals(other.getDescription()))
            return false;
        return true;
    }
}
