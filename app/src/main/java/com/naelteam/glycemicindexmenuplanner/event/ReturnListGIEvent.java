package com.naelteam.glycemicindexmenuplanner.event;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.network.error.BaseError;

import java.util.List;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnListGIEvent {

    private List<GlycemicIndex> mGlycemicIndexes;

    private BaseError mError;

    public ReturnListGIEvent(List<GlycemicIndex> glycemicIndexes, BaseError error){
        mGlycemicIndexes = glycemicIndexes;
        mError = error;
    }

    public List<GlycemicIndex> getGlycemicIndexes() {
        return mGlycemicIndexes;
    }

    public BaseError getError() {
        return mError;
    }

}
