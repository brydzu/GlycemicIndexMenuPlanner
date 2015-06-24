package com.naelteam.glycemicindexmenuplanner;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * Created by fab on 24/06/15.
 */
public interface AppCompatActivityInterface {
    void setSupportActionBar(Toolbar mToolbar);

    ActionBar getSupportActionBar();

    void setToolbar(Toolbar mToolbar);
}
