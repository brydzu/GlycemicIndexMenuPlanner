package com.naelteam.glycemicindexmenuplanner.event;

/**
 * Created by fab on 21/06/15.
 */
public class UISearchGIEvent {

    private String mSearchText;

    public UISearchGIEvent(String searchTxt){
        mSearchText = searchTxt;
    }

    public String getSearchText() {
        return mSearchText;
    }
}
