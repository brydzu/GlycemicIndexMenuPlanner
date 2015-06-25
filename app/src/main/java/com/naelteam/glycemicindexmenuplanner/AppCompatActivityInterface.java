package com.naelteam.glycemicindexmenuplanner;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;

/**
 * Created by fab on 24/06/15.
 */
public interface AppCompatActivityInterface {
    ActionBar getSupportActionBar();

    void setSupportActionBar(Toolbar mToolbar);

    void setToolbar(Toolbar mToolbar);

    void onDisplayGlycemicIndexDetails(GlycemicIndex glycemicIndex);

    void onInitDrawerLayout();

    void hideNavigationDrawer();

    void showNavigationDrawer();
}
