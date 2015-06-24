package com.naelteam.glycemicindexmenuplanner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.adapter.ListGIRecyclerAdapter;
import com.naelteam.glycemicindexmenuplanner.dialog.DialogListener;
import com.naelteam.glycemicindexmenuplanner.dialog.SearchGIDialog;
import com.naelteam.glycemicindexmenuplanner.event.UISearchGIEvent;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.presenter.ListGIPresenter;
import com.naelteam.glycemicindexmenuplanner.view.DividerItemDecoration;
import com.naelteam.glycemicindexmenuplanner.view.SlideInRightAnimator;

import java.util.List;

import de.greenrobot.event.EventBus;


public class ListGIFragment extends Fragment implements ListGIPresenter.Listener {

    private static final String TAG = ListGIFragment.class.getSimpleName();
    private static final String DATA_LIST_KEY = "DATA_LIST_KEY";

    private AppCompatActivityInterface mActivityInterface;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ListGIPresenter mListGIPresenter;
    private ListGIRecyclerAdapter mAdapter;

    private ProgressDialog mProgressdialog;

    public ListGIFragment() {
    }

    public static Fragment newInstance() {
        return new ListGIFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate - savedInstanceState = " + savedInstanceState);

        mListGIPresenter = new ListGIPresenter(this);

        if (savedInstanceState !=null){
            mListGIPresenter.setDataList(savedInstanceState.getParcelable(DATA_LIST_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_gi, container, false);

        return view;
    }

    private void initFloatingActionBar() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchGIDialog().createAlertDialog(getActivity(), new DialogListener<String>() {
                    @Override
                    public void onPositiveClick(DialogInterface dialogInterface, String result) {
                        Log.d(TAG, "onPositiveClick - result = " + result);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFloatingActionBar();
        initToolbar();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ListGIRecyclerAdapter(getActivity(), mListGIPresenter.getInitialDatas(), new ListGIRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onLoadGIGroup(GlycemicIndexGroup glycemicIndexGroup, View itemView, int layoutPosition) {
                Log.d(TAG, "onLoadGIGroup - glycemicIndexGroup.isExpanded() = " + glycemicIndexGroup.isExpanded());
                if (glycemicIndexGroup.isExpanded()){
                    mAdapter.collapseGlycemicGroup(glycemicIndexGroup, layoutPosition);
                }else {
                    List<IGlycemicIndex> glycemicIndexes = mListGIPresenter.getData(glycemicIndexGroup.getTitle());
                    if (glycemicIndexes.size() > 0) {
                        mAdapter.expandGlycemicGroup(glycemicIndexGroup, glycemicIndexes, layoutPosition);
                        if (itemView != null) {
                            Snackbar.make(itemView, "Loading data..", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onClickGIItem(View view, int layoutPosition) {
                Log.d(TAG, "onClickGIItem - ");
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        SlideInRightAnimator animator = new SlideInRightAnimator();
        animator.setItemAnimatorFinishedListener(new RecyclerView.ItemAnimator.ItemAnimatorFinishedListener() {
            @Override
            public void onAnimationsFinished() {
                        mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(DATA_LIST_KEY, mListGIPresenter.getDataList());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart - ");

        if (mListGIPresenter.getDataCount() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mProgressdialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
            mListGIPresenter.listGlycementIndexes();
        }else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume - ");

        mListGIPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mListGIPresenter.pause();
    }

    private void closeProgressDialog(){
        if (mProgressdialog != null){
            mProgressdialog.cancel();
        }
    }

    @Override
    public void onSearchGI(List<IGlycemicIndex> glycemicIndexes) {
        int firstPosition = mAdapter.selectItems(glycemicIndexes);
        mRecyclerView.smoothScrollToPosition(firstPosition);
    }

    @Override
    public void onListGISuccess() {
        closeProgressDialog();
        Log.d(TAG, "onListGISuccess - render visible");
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListGIError() {
        closeProgressDialog();
        Toast.makeText(getActivity(), "Unable to get datas, please try again", Toast.LENGTH_SHORT);
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
}
