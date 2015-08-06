package com.naelteam.glycemicindexmenuplanner.utils;

import android.os.Looper;

/**
 * Created by fab on 06/08/15.
 */
public class ThreadUtils {

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
