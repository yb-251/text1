package com.example.text1.view.fragment;

import android.content.Intent;

import androidx.core.content.ContextCompat;


import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.DataListInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.DataModel;
import com.example.text1.R;
import com.example.text1.adapter.DataGroupListAdapter;
import com.example.text1.interfaces.OnRecyclerItemClickListener;
import com.example.text1.view.activity.HomeActivity;
import com.example.text1.view.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class DataGroupFragment extends BaseMvpFragment<DataModel> implements OnRecyclerItemClickListener {

    private int pageNum = 1;
    private List<DataListInfo> mList = new ArrayList<>();
    private DataGroupListAdapter mAdapter;
    private HomeActivity mActivity;

    public static DataGroupFragment getInstance() {
        return new DataGroupFragment();
    }

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new DataGroupListAdapter(getContext(), mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.light_back));
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void setUpData() {
        mActivity = (HomeActivity) getActivity();
        mPresenter.getDataWithLoadType(ApiConfig.DATA_GROUP_LIST, LoadType.NORMAL, pageNum, mActivity.fid);
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
        if (loadType == LoadType.REFRESH){
            mRefreshLayout.finishRefresh();
            mList.clear();
        } else if (loadType == LoadType.MORE){
            mRefreshLayout.finishLoadMore();
            if (base.result.size() < 10){
                mRefreshLayout.setNoMoreData(true);
            }
        }
        mList.addAll(base.result);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListLoadMore() {
        pageNum++;
        mPresenter.getDataWithLoadType(ApiConfig.DATA_GROUP_LIST, LoadType.MORE, pageNum, mActivity.fid);
    }

    @Override
    public void onListRefresh() {
        pageNum=1;
        mPresenter.getDataWithLoadType(ApiConfig.DATA_GROUP_LIST, LoadType.REFRESH, pageNum, mActivity.fid);
    }

    @Override
    public void onItemClick(Object[] pos) {
        int position = (int) pos[0];
        if ((int) pos[1] == mAdapter.FOCUS){
            startActivity(new Intent(getContext(), LoginActivity.class));
        } else {

        }
    }
}
