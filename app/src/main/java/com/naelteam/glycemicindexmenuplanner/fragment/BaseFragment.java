package com.naelteam.glycemicindexmenuplanner.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.dialog.DialogListener;
import com.naelteam.glycemicindexmenuplanner.dialog.SearchGIDialog;
import com.naelteam.glycemicindexmenuplanner.event.UISearchGIEvent;

import de.greenrobot.event.EventBus;


public abstract class BaseFragment extends Fragment{

    protected AppCompatActivityInterface mActivityInterface;
    protected Toolbar mToolbar;
    protected AppBarLayout mAppBarLayout;
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected FloatingActionButton mFloatingActionButton;

    protected boolean mDisplayFloatingButton = true;
    protected ImageView mImageToolbar;

    protected abstract String getLogTag();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mDisplayFloatingButton) {
            initFloatingActionBar();
        }
        initToolbar();
        initCollapsingToolbar();

        mActivityInterface.onInitDrawerLayout();
    }

    protected void setAppBarLayoutHeight(int height){
        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        layoutParams.height = height;
        mAppBarLayout.setLayoutParams(layoutParams);
    }

    private void initCollapsingToolbar(){
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(android.R.style.TextAppearance_Medium);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));

        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(android.R.style.TextAppearance_Large);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.black));

        mImageToolbar = (ImageView) getView().findViewById(R.id.toolbar_image);
        setCollapsingToolbarLayoutTitle(getResources().getString(R.string.app_name));
    }

    private void initFloatingActionBar() {
        mFloatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFloatingActionBarClick(v);
            }
        });
    }

    protected abstract void onFloatingActionBarClick(View view);

    protected void setCollapsingToolbarLayoutTitle(String title){
        mCollapsingToolbarLayout.setTitle(title);
    }

    private void initToolbar() {
        mAppBarLayout = (AppBarLayout) getView().findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mActivityInterface.setToolbar(mToolbar);
        mActivityInterface.setSupportActionBar(mToolbar);
        final ActionBar actionBar = mActivityInterface.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
