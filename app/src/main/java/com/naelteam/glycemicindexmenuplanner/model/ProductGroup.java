package com.naelteam.glycemicindexmenuplanner.model;

/**
 */
public class ProductGroup implements IGlycemicIndex{

    private String mTitle;

    private boolean mExpanded;

    private Integer mNbItems;

    public ProductGroup(String title) {
        mTitle = title;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean mExpanded) {
        this.mExpanded = mExpanded;
    }

    public Integer getNbItems() {
        return mNbItems;
    }

    public void setNbItems(Integer mNbItems) {
        this.mNbItems = mNbItems;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mTitle == null) ? 0 : mTitle.hashCode());
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
        ProductGroup other = (ProductGroup) obj;
        if (mTitle == null) {
            if (other.getTitle() != null)
                return false;
        } else if (!mTitle.equals(other.getTitle()))
            return false;
        return true;
    }
}
