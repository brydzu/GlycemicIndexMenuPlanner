package com.naelteam.glycemicindexmenuplanner.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.dialog.DialogListener;
import com.naelteam.glycemicindexmenuplanner.dialog.SearchGIDialog;
import com.naelteam.glycemicindexmenuplanner.event.UISearchGIEvent;

import de.greenrobot.event.EventBus;


public abstract class BaseFragment extends Fragment{

    protected AppCompatActivityInterface mActivityInterface;
    protected Toolbar mToolbar;
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected FloatingActionButton mFloatingActionButton;

    protected boolean mDisplayFloatingButton = true;

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

    private void initCollapsingToolbar(){
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
        setCollapsingToolbarLayoutTitle(getResources().getString(R.string.app_name));
    }

    private void initFloatingActionBar() {
        mFloatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchGIDialog().createAlertDialog(getActivity(), new DialogListener<String>() {
                    @Override
                    public void onPositiveClick(DialogInterface dialogInterface, String result) {
                        Log.d(getLogTag(), "onPositiveClick - result = " + result);
                        EventBus.getDefault().post(new UISearchGIEvent(result));
                    }
                    @Override
                    public void onNegativeClick(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    protected void setCollapsingToolbarLayoutTitle(String title){
        mCollapsingToolbarLayout.setTitle(title);
    }

    private void initToolbar() {
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
