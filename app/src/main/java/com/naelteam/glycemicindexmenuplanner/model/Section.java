package com.naelteam.glycemicindexmenuplanner.model;

import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by fab on 12/07/15.
 */
public class Section {

    private String mTitle;
    private List<Section> mSections;
    private String mDescription;
    private String[] mImagesUrl;
    private boolean subTitle;

    public Section(String title){
        this.mTitle = title;
    }

    public Section() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public List<Section> getSections() {
        return mSections;
    }

    public void addSection(Section section) {
        if (mSections == null) {
            mSections = new ArrayList<Section>();
        }
        this.mSections.add(section);
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

    public boolean isSubTitle() {
        return subTitle;
    }

    public void setSubTitle(boolean subTitle) {
        this.subTitle = subTitle;
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
        Section other = (Section) obj;
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

    public void clearSections() {
        if (mSections!=null){
            mSections.clear();
        }
        mSections = null;
    }

    public void setImagesUrlStr(String flatImagesUrl) {
        if (flatImagesUrl != null && flatImagesUrl.length() > 0){
            StringTokenizer tokenizer = new StringTokenizer(flatImagesUrl, "|");
            mImagesUrl = new String[tokenizer.countTokens()];
            int count = 0;
            while (tokenizer.hasMoreTokens()){
                mImagesUrl[count] = tokenizer.nextToken();
            }
        }
    }

    public String getImagesUrlStr() {
        String flatImagesUrl = null;
        if (mImagesUrl != null && mImagesUrl.length > 0){
            flatImagesUrl = "";
            for (String image:mImagesUrl){
                flatImagesUrl += "|" + image;
            }
        }
        return flatImagesUrl;
    }

    public void setSections(List<Section> subSections) {
        this.mSections = subSections;
    }
}
