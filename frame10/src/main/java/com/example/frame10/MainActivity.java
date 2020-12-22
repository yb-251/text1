package com.example.frame10;

import android.view.View;

import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.DemoInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.demo.DemoAdapter;
import com.example.frame10.demo.DemoModel;
import com.example.frame10.frame.BaseMvpActivity;
import com.example.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMvpActivity<DemoModel> {
    private List<DemoInfo.DemoInnerDataInfo> mData = new ArrayList<>();
    private List<String> hasFocusList = new ArrayList<>();
    private DemoAdapter mAdapter;
    private int pageId = 0;
    private final String FOCUS_KEY = "focus_key";

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new DemoAdapter(mData, this, hasFocusList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClick(pos -> {
            if (hasFocusList.contains(mData.get(pos).id)) hasFocusList.remove(mData.get(pos).id);
            else hasFocusList.add(mData.get(pos).id);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onListRefresh() {
        pageId = 0;
        mPresenter.getDataWithLoadType(ApiConfig.DEMO_TEST, LoadType.REFRESH, pageId);
    }

    @Override
    public void onListLoadMore() {
        pageId += 1;
        mPresenter.getDataWithLoadType(ApiConfig.DEMO_TEST, LoadType.MORE, pageId);
    }

    @Override
    public void setUpData() {
        List<String> tempList = SharedPrefrenceUtils.getStringList(this, FOCUS_KEY);
        if (tempList != null && tempList.size() != 0) hasFocusList.addAll(tempList);
        mPresenter.getDataWithLoadType(ApiConfig.DEMO_TEST, LoadType.NORMAL, pageId);
//        mPresenter.getData(ApiConfig.SEND_VERIFY, "+8615100133517");
    }

    @Override
    public DemoModel setModel() {
        return new DemoModel();
    }

    @Override
    public void onDataBack(int whichApi, Object[] pObjects) {
        BaseInfo info = (BaseInfo) pObjects[0];
        showToast(info.errNo);
    }

    @Override
    public void onDataBackWithLoadType(int whichApi, int loadType, Object... pObjects) {
        if (loadType == LoadType.REFRESH) {
            mRefreshLayout.finishRefresh();
            mData.clear();
        } else if (loadType == LoadType.MORE) mRefreshLayout.finishLoadMore();
        DemoInfo info = (DemoInfo) pObjects[0];
        mData.addAll(info.datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putStringList(this, FOCUS_KEY, hasFocusList);
    }

    public void textClick(View view) {
        mPresenter.getData(ApiConfig.SEND_VERIFY, "+8615100133517");
    }
}
