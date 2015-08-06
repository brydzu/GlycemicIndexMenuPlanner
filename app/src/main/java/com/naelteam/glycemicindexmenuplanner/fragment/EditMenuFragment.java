package com.naelteam.glycemicindexmenuplanner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.presenter.DetailsGIPresenter;
import com.naelteam.glycemicindexmenuplanner.utils.UnitUtils;


public class EditMenuFragment extends BaseFragment{

    private static final String TAG = EditMenuFragment.class.getSimpleName();

    public static final String GLYCEMIC_INDEX = "GLYCEMIC_INDEX";

    private ProgressDialog mProgressdialog;
    private DetailsGIPresenter mDetailsGIPresenter;
    private boolean initUI;
    private Interpolator mFastOutSlowInInterpolator;

    public EditMenuFragment() {
        mDisplayFloatingButton = false;
    }

    public static Fragment newInstance() {
        EditMenuFragment fragment = new EditMenuFragment();
        /*Bundle bundle = new Bundle();
        bundle.putParcelable(GLYCEMIC_INDEX, glycemicIndex);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate - savedInstanceState = " + savedInstanceState);

        //mDetailsGIPresenter = new DetailsGIPresenter(this);

        if (savedInstanceState !=null){
            //mDetailsGIPresenter.setDataList(savedInstanceState.getParcelable(DATA_LIST_KEY));
        }

        /*Bundle arguments = getArguments();
        if (arguments!=null){
            Product product = arguments.getParcelable(GLYCEMIC_INDEX);
            if (product != null){
                mProduct = product;
            }
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_slow_in);

        setAppBarLayoutHeight((int) UnitUtils.dpToPixels(getActivity(), 120));

        setCollapsingToolbarLayoutTitle(getString(R.string.edit_menu_title));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));

        getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                addFloatingActionButtons((ViewGroup) v);
            }
        });

        mActivityInterface.closeNavigationDrawer();
    }

    private void initViews() {
        EditText editText = (EditText) getView().findViewById(R.id.edit_menu_servings);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onKey - ");
                if (s != null && s.length()>0) {
                    showCheckFloatingActionBar();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void addFloatingActionButtons(final ViewGroup container) {
        Log.d(TAG, "addCheckFloatingActionButton - initUI = " + initUI);

        if (!initUI) {
            initUI = true;

            final int fabSize = getResources().getDimensionPixelSize(R.dimen.fab_size);
            final int fabPadding = getResources().getDimensionPixelSize(R.dimen.padding_fab);

            addCaptureFloatingActionBar(container, fabSize, fabPadding);
            addCheckFloatingActionButton(container, fabSize, fabPadding);
        }
    }

    private void addCaptureFloatingActionBar(final ViewGroup container, int fabSize, int fabPadding) {
        final FrameLayout.LayoutParams captureFabLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END | Gravity.TOP);
        captureFabLayoutParams.setMargins(mCollapsingToolbarLayout.getRight() - (2*fabSize) - 30, mCollapsingToolbarLayout.getBottom() - (fabSize/2), 0, fabPadding);

        final FloatingActionButton captureFab = new FloatingActionButton(getActivity());
        captureFab.setId(R.id.capture_fab);
        captureFab.setVisibility(View.VISIBLE);
        captureFab.setImageResource(R.drawable.ic_camera_white_48dp);
        captureFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        container.post(new Runnable() {
            @Override
            public void run() {
                container.addView(captureFab, captureFabLayoutParams);
            }
        });
    }

    private void showCheckFloatingActionBar(){
        final FloatingActionButton checkFab = (FloatingActionButton) getView().findViewById(R.id.check_fab);
        checkFab.setVisibility(View.VISIBLE);
        checkFab.animate().scaleX(1f).scaleY(1f)
                .setInterpolator(mFastOutSlowInInterpolator);
    }

    private void addCheckFloatingActionButton(final ViewGroup container, int fabSize, int fabPadding) {

        final FrameLayout.LayoutParams checkFabLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.END | Gravity.TOP);
        checkFabLayoutParams.setMargins(mCollapsingToolbarLayout.getRight() - fabSize - 15, mCollapsingToolbarLayout.getBottom() - (fabSize/2), 0, fabPadding);

        final FloatingActionButton checkFab = new FloatingActionButton(getActivity());
        checkFab.setId(R.id.check_fab);
        checkFab.setScaleX(0);
        checkFab.setScaleY(0);
        checkFab.setVisibility(View.GONE);
        checkFab.setImageResource(R.drawable.ic_check_white_48dp);
        checkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        container.post(new Runnable() {
            @Override
            public void run() {
                container.addView(checkFab, checkFabLayoutParams);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart - ");

        /*mProgressdialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        mDetailsGIPresenter.searchDetailsOnGlycemicIndex(mProduct.getTitle());*/
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop - ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy - ");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume - ");

        //mDetailsGIPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        //mDetailsGIPresenter.stop();
    }

    @Override
    protected void onFloatingActionBarClick(View view) {

    }

    private void closeProgressDialog() {
        if (mProgressdialog!=null){
            mProgressdialog.dismiss();
            mProgressdialog=null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach - ");

        try {
            mActivityInterface = (AppCompatActivityInterface) activity;
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach - exception ", e);
        }
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }
}
