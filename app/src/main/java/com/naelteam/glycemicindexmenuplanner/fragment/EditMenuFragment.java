package com.naelteam.glycemicindexmenuplanner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.adapter.DetailsGIRecyclerAdapter;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.WikProduct;
import com.naelteam.glycemicindexmenuplanner.model.WikSection;
import com.naelteam.glycemicindexmenuplanner.presenter.DetailsGIPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class EditMenuFragment extends BaseFragment implements DetailsGIPresenter.Listener{

    public static final String GLYCEMIC_INDEX = "GLYCEMIC_INDEX";
    private static final String TAG = EditMenuFragment.class.getSimpleName();
    private ProgressDialog mProgressdialog;
    private DetailsGIPresenter mDetailsGIPresenter;
    private GlycemicIndex mGlycemicIndex;
    private DetailsGIRecyclerAdapter mDetailsGIRecyclerAdapter;
    private RecyclerView mRecyclerView;

    public EditMenuFragment() {
        mDisplayFloatingButton = false;
    }

    public static Fragment newInstance(GlycemicIndex glycemicIndex) {
        EditMenuFragment fragment = new EditMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCollapsingToolbarLayoutTitle(getString(R.string.gi_details_title));
        mActivityInterface.hideNavigationDrawer();

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.details_gi_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mActivityInterface.showNavigationDrawer();

        mDetailsGIRecyclerAdapter = new DetailsGIRecyclerAdapter(getActivity(), new DetailsGIRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onLoadGIGroup(GlycemicIndexGroup glycemicIndexGroup, View itemView, int layoutPosition) {
            }

            @Override
            public void onClickGIItem(GlycemicIndex glycemicIndex, int layoutPosition) {
            }
        });

        mRecyclerView.setAdapter(mDetailsGIRecyclerAdapter);
        mRecyclerView.setVisibility(View.GONE);
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

        final Context context = getActivity();
        if (wikProduct != null){
            Log.d(TAG, "handleMessage - getThumbnailUrl = " + wikProduct.getThumbnailUrl());

            setCollapsingToolbarLayoutTitle(wikProduct.getTitle());

            mImageToolbar.setVisibility(View.VISIBLE);
            Picasso.with(context).load(wikProduct.getThumbnailUrl()).into(mImageToolbar);

            Log.d(TAG, "handleMessage - description = " + wikProduct.getDescription());

            //flatten section list for the recyclerview
            List<WikSection> wikSections = new ArrayList<WikSection>();

            // add description as first row of the list
            WikSection wikSection = new WikSection();
            wikSection.setDescription(wikProduct.getDescription());
            wikSections.add(wikSection);

            for (WikSection section:wikProduct.getWikSections()){
                WikSection newSectionTitle = new WikSection(section.getTitle());
                wikSections.add(newSectionTitle);
                if (section.getDescription()!=null && (!section.getDescription().isEmpty())) {
                    section.setTitle(null);
                    wikSections.add(section);
                }
                if (section.getSections()!=null) {
                    for (WikSection subSection : section.getSections()) {
                        WikSection newSubSectionTitle = new WikSection(subSection.getTitle());
                        newSubSectionTitle.setSubTitle(true);
                        wikSections.add(newSubSectionTitle);
                        subSection.setTitle(null);
                        wikSections.add(subSection);
                    }
                    section.clearSections();
                }
            }
            Log.d(TAG, "handleMessage - flatten wikSections size = " +  wikSections.size());
            mDetailsGIRecyclerAdapter.addAll(wikSections, 0, wikSections.size());
            mRecyclerView.setVisibility(View.VISIBLE);

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

        mActivityInterface.onSearchDetailGIError();
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
