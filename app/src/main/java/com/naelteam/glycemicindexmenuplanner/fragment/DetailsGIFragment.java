package com.naelteam.glycemicindexmenuplanner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.presenter.DetailsGIPresenter;
import com.squareup.picasso.Picasso;


public class DetailsGIFragment extends BaseFragment implements DetailsGIPresenter.Listener{

    public static final String GLYCEMIC_INDEX = "GLYCEMIC_INDEX";
    private static final String TAG = DetailsGIFragment.class.getSimpleName();
    private ProgressDialog mProgressdialog;
    private DetailsGIPresenter mDetailsGIPresenter;
    private GlycemicIndex mGlycemicIndex;

    private TextView mDescriptionView;

    public DetailsGIFragment() {
        mDisplayFloatingButton = false;
    }

    public static Fragment newInstance(GlycemicIndex glycemicIndex) {
        DetailsGIFragment fragment = new DetailsGIFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GLYCEMIC_INDEX, glycemicIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate - savedInstanceState = " + savedInstanceState);

        mDetailsGIPresenter = new DetailsGIPresenter(this);

        if (savedInstanceState !=null){
            //mDetailsGIPresenter.setDataList(savedInstanceState.getParcelable(DATA_LIST_KEY));
        }

        Bundle arguments = getArguments();
        if (arguments!=null){
            GlycemicIndex glycemicIndex = arguments.getParcelable(GLYCEMIC_INDEX);
            if (glycemicIndex != null){
                mGlycemicIndex = glycemicIndex;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_gi, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDescriptionView = (TextView) getView().findViewById(R.id.details_gi_description);

        setCollapsingToolbarLayoutTitle(getString(R.string.gi_details_title));
        mActivityInterface.hideNavigationDrawer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart - ");

        mProgressdialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
        mDetailsGIPresenter.searchDetailsOnGlycemicIndex(mGlycemicIndex.getTitle());
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

        mDetailsGIPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mDetailsGIPresenter.pause();
    }

    @Override
    protected void onFloatingActionBarClick(View view) {

    }

    @Override
    public void onSearchDetailGISuccess(final WikProduct wikProduct) {
        closeProgressDialog();

        Log.d(TAG, "onSearchDetailGISuccess - wikProduct = " + wikProduct);

        if (wikProduct != null){
            Handler handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    Log.d(TAG, "handleMessage - getThumbnailUrl = " + wikProduct.getThumbnailUrl());

                    mImageToolbar.setVisibility(View.VISIBLE);
                    Picasso.with(getActivity()).load(wikProduct.getThumbnailUrl()).into(mImageToolbar);

                    Log.d(TAG, "handleMessage - description = " + wikProduct.getDescription());

                    mDescriptionView.setText(wikProduct.getDescription());
                }
            };

            Message message = Message.obtain(handler);
            message.sendToTarget();

        }
    }

    private void closeProgressDialog() {
        if (mProgressdialog!=null){
            mProgressdialog.dismiss();
            mProgressdialog=null;
        }
    }

    @Override
    public void onSearchDetailGIError() {
        closeProgressDialog();
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
