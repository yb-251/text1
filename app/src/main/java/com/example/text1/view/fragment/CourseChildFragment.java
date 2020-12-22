package com.example.text1.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.datalibrary.CourseChildListInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.CourseModel;
import com.example.text1.R;
import com.example.text1.adapter.CourseChildAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CourseChildFragment extends BaseMvpFragment<CourseModel> {
    private static int whichFragment;
    private static final String WHICH_FRAGMENT_LABEL = "label";
    private int mFragmentLabel;
    private int pageNum = 1;
    private List<CourseChildListInfo.ListContent> mListData = new ArrayList<>();
    private CourseChildAdapter mAdapter;

    public static CourseChildFragment getInstance(int pWhichFragment) {
        whichFragment = pWhichFragment;
        CourseChildFragment fragment = new CourseChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_FRAGMENT_LABEL, whichFragment);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentLabel = getArguments().getInt(WHICH_FRAGMENT_LABEL);
        }
    }

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new CourseChildAdapter(mListData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getDataWithLoadType(ApiConfig.COURSE_CHILD_LIST, LoadType.NORMAL, mFragmentLabel, pageNum);
    }

    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }

    @Override
    public void onListRefresh() {
        pageNum = 1;
        mPresenter.getDataWithLoadType(ApiConfig.COURSE_CHILD_LIST, LoadType.REFRESH, mFragmentLabel, pageNum);
    }

    @Override
    public void onListLoadMore() {
        pageNum++;
        mPresenter.getDataWithLoadType(ApiConfig.COURSE_CHILD_LIST, LoadType.MORE, mFragmentLabel, pageNum);
    }

    @Override
    public void onDataBackWithLoadType(int whichApi, int loadType, Object... pObjects) {
        JsonObject base = (JsonObject) pObjects[0];
        String s = base.toString();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray lists = result.getJSONArray("lists");
            for (int i = 0; i < lists.length(); i++) {
                JSONObject o = (JSONObject) lists.get(i);
                if (o.get("rate") instanceof JSONObject) {
                    JSONObject rate = o.getJSONObject("rate");
                    String value = rate.getString("value");
                    o.remove("rate");
                    o.put("rate", value);
                }
            }
            Gson gson = new Gson();
            CourseChildListInfo info = gson.fromJson(result.toString(), CourseChildListInfo.class);
            if (loadType == LoadType.REFRESH){
                mRefreshLayout.finishRefresh();
                mListData.clear();
            } else if (loadType == LoadType.MORE){
                mRefreshLayout.finishLoadMore();
                if (info.lists.size() == 0){
                    showToast("没有更多数据了");
                    mRefreshLayout.setNoMoreData(true);
                }
            }
            mListData.addAll(info.lists);
            mAdapter.notifyDataSetChanged();
        } catch (JSONException pE) {
            pE.printStackTrace();
        }
    }
}