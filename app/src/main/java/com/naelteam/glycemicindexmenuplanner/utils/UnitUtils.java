package com.naelteam.glycemicindexmenuplanner.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by fab on 06/08/15.
 */
public class UnitUtils {

    public static float dpToPixels(Context context, int value){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.getDisplayMetrics());
    }

}
