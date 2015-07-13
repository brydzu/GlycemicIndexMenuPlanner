package com.naelteam.glycemicindexmenuplanner.event;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;

import java.util.List;

/**
 * Created by fab on 09/06/15.
 */
public class ReturnListGIReturnEvent extends BaseReturnEvent {

    private List<GlycemicIndex> mGlycemicIndexes;

    public ReturnListGIReturnEvent(List<GlycemicIndex> glycemicIndexes, Throwable error){
        super(error);
        mGlycemicIndexes = glycemicIndexes;
    }

    public List<GlycemicIndex> getGlycemicIndexes() {
        return mGlycemicIndexes;
    }


}
