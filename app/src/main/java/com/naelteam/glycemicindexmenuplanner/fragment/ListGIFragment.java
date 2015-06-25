package com.naelteam.glycemicindexmenuplanner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naelteam.glycemicindexmenuplanner.AppCompatActivityInterface;
import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.adapter.ListGIRecyclerAdapter;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.model.GlycemicIndexGroup;
import com.naelteam.glycemicindexmenuplanner.model.IGlycemicIndex;
import com.naelteam.glycemicindexmenuplanner.presenter.ListGIPresenter;
import com.naelteam.glycemicindexmenuplanner.view.DividerItemDecoration;
import com.naelteam.glycemicindexmenuplanner.view.SlideInRightAnimator;

import java.util.List;


public class ListGIFragment extends BaseFragment implements ListGIPresenter.Listener {

    private static final String TAG = ListGIFragment.class.getSimpleName();
    private static final String DATA_LIST_KEY = "DATA_LIST_KEY";

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated - ");

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mActivityInterface.showNavigationDrawer();

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
                            Snackbar.make(itemView, getString(R.string.loading_data), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onClickGIItem(GlycemicIndex glycemicIndex, int layoutPosition) {
                Log.d(TAG, "onClickGIItem - ");
                mActivityInterface.onDisplayGlycemicIndexDetails(glycemicIndex);
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

        Log.d(TAG, "onPause - ");

        mListGIPresenter.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop - ");
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
        Log.d(TAG, "onListGISuccess");
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

    @Override
    protected String getLogTag() {
        return TAG;
    }
}
