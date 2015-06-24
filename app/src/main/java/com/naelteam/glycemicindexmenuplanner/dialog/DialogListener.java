package com.naelteam.glycemicindexmenuplanner.dialog;

import android.content.DialogInterface;

/**
 * Created by fab on 21/06/15.
 */
public interface DialogListener<T> {
    void onPositiveClick(DialogInterface dialogInterface, T result);
    void onNegativeClick(DialogInterface dialogInterface);
}
