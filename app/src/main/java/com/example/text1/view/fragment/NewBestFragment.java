package com.example.text1.view.fragment;


import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.DataListInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.DataModel;
import com.example.text1.R;
import com.example.text1.adapter.NewBestAdapter;
import com.example.text1.view.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class NewBestFragment extends BaseMvpFragment<DataModel> {

    private HomeActivity mA;
    private List<DataListInfo> mList = new ArrayList();
    private NewBestAdapter mAdapter;

    public static NewBestFragment getInstance() {
        return new NewBestFragment();
    }

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new NewBestAdapter(mList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mA = (HomeActivity) getActivity();
        mPresenter.getDataWithLoadType(ApiConfig.DATA_BEST_LIST, LoadType.NORMAL, page, mA.fid);
    }

    @Override
    public void onListRefresh() {
        page = 1;
        mPresenter.getDataWithLoadType(ApiConfig.DATA_BEST_LIST, LoadType.REFRESH, page, mA.fid);
    }

    @Override
    public void onListLoadMore() {
        page++;
        mPresenter.getDataWithLoadType(ApiConfig.DATA_BEST_LIST, LoadType.MORE, page, mA.fid);
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }

    @Override
    public void onDataBackWithLoadType(int whichApi, int loadType, Object... pObjects) {
        BaseInfo<List<DataListInfo>> base = (BaseInfo<List<DataListInfo>>) pObjects[0];
        if (loadType == LoadType.REFRESH) {
            mRefreshLayout.finishRefresh();
            mList.clear();
        } else if (loadType == LoadType.MORE) {
            mRefreshLayout.finishLoadMore();
            if (base.result.size() < 10) {
                mRefreshLayout.setNoMoreData(true);
            }
        }
        mList.addAll(base.result);
        mAdapter.notifyDataSetChanged();
    }
}
