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
import com.naelteam.glycemicindexmenuplanner.model.Product;
import com.naelteam.glycemicindexmenuplanner.model.ProductGroup;
import com.naelteam.glycemicindexmenuplanner.model.Section;
import com.naelteam.glycemicindexmenuplanner.presenter.DetailsGIPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsGIFragment extends BaseFragment implements DetailsGIPresenter.Listener{

    public static final String GLYCEMIC_INDEX = "GLYCEMIC_INDEX";
    private static final String TAG = DetailsGIFragment.class.getSimpleName();
    private ProgressDialog mProgressdialog;
    private DetailsGIPresenter mDetailsGIPresenter;
    private Product mProduct;
    private DetailsGIRecyclerAdapter mDetailsGIRecyclerAdapter;
    private RecyclerView mRecyclerView;

    public DetailsGIFragment() {
        mDisplayFloatingButton = false;
    }

    public static Fragment newInstance(Product product) {
        DetailsGIFragment fragment = new DetailsGIFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GLYCEMIC_INDEX, product);
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
            Product product = arguments.getParcelable(GLYCEMIC_INDEX);
            if (product != null){
                mProduct = product;
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

        setCollapsingToolbarLayoutTitle(getString(R.string.gi_details_title));
        mActivityInterface.closeNavigationDrawer();

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.details_gi_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mActivityInterface.showNavigationDrawer();

        mDetailsGIRecyclerAdapter = new DetailsGIRecyclerAdapter(getActivity(), new DetailsGIRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onLoadGIGroup(ProductGroup productGroup, View itemView, int layoutPosition) {
            }

            @Override
            public void onClickGIItem(Product product, int layoutPosition) {
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
        mDetailsGIPresenter.searchDetailsOnGlycemicIndex(mProduct.getTitle());
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
    public void onSearchDetailGISuccess(final Product product) {
        closeProgressDialog();

        Log.d(TAG, "onSearchDetailGISuccess - product = " + product);

        final Context context = getActivity();
        if (product != null){
            Log.d(TAG, "handleMessage - getThumbnailUrl = " + product.getThumbnailUrl());

            setCollapsingToolbarLayoutTitle(product.getTitle());

            mImageToolbar.setVisibility(View.VISIBLE);
            Picasso.with(context).load(product.getThumbnailUrl()).into(mImageToolbar);

            Log.d(TAG, "handleMessage - description = " + product.getDescription());

            //flatten section list for the recyclerview
            List<Section> sections = new ArrayList<Section>();

            // add description as first row of the list
            Section wikSection = new Section();
            wikSection.setDescription(product.getDescription());
            sections.add(wikSection);

            for (Section section:product.getSections()){
                Section newSectionTitle = new Section(section.getTitle());
                sections.add(newSectionTitle);
                if (section.getDescription()!=null && (!section.getDescription().isEmpty())) {
                    section.setTitle(null);
                    sections.add(section);
                }
                if (section.getSections()!=null) {
                    for (Section subSection : section.getSections()) {
                        Section newSubSectionTitle = new Section(subSection.getTitle());
                        newSubSectionTitle.setSubTitle(true);
                        sections.add(newSubSectionTitle);
                        subSection.setTitle(null);
                        sections.add(subSection);
                    }
                    section.clearSections();
                }
            }
            Log.d(TAG, "handleMessage - flatten sections size = " +  sections.size());
            mDetailsGIRecyclerAdapter.addAll(sections, 0, sections.size());
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
