package com.naelteam.glycemicindexmenuplanner.event;

/**
 * Created by fab on 09/06/15.
 */
public class SearchGIByNameEvent {

    private String mSearchStr;

    public SearchGIByNameEvent(String searchStr){
        mSearchStr = searchStr;
    }

    public String getSearchStr() {
        return mSearchStr;
    }
}
