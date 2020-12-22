package com.example.text1.view.fragment;

import androidx.fragment.app.Fragment;

import com.example.datalibrary.BannerDataInfo;
import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.Carousel;
import com.example.datalibrary.LiveDataInfo;
import com.example.datalibrary.MainPageListInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.MainPageModel;
import com.example.text1.R;
import com.example.text1.adapter.MainPageListAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends BaseMvpFragment<MainPageModel> {

    private int pageNum = 1;
    private BaseInfo<List<MainPageListInfo>> mBaseList;
    private List<BannerDataInfo> mBannerList = new ArrayList<>();
    private List<MainPageListInfo> mListData = new ArrayList<>();
    private List<LiveDataInfo> mLiveData = new ArrayList<>();
    private MainPageListAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new MainPageListAdapter(getActivity(), mBannerList, mListData, mLiveData);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListLoadMore() {
        pageNum += 1;
        mPresenter.getDataWithLoadType(ApiConfig.MAIN_PAGE_LIST, LoadType.MORE, pageNum);
    }

    @Override
    public void onListRefresh() {
        pageNum = 1;
        mPresenter.getDataWithLoadType(ApiConfig.MAIN_PAGE_LIST, LoadType.REFRESH, pageNum);
        mPresenter.getData(ApiConfig.BANNER_DATA);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.BANNER_DATA);
        mPresenter.getDataWithLoadType(ApiConfig.MAIN_PAGE_LIST, LoadType.NORMAL, pageNum);
    }

    @Override
    public MainPageModel setModel() {
        return new MainPageModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {
        switch (whichApi) {
            case ApiConfig.BANNER_DATA:
                JsonObject object = (JsonObject) pObjects[0];
                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    JSONObject result = jsonObject.getJSONObject("result");
                    if (result.get("live") instanceof Boolean) {
                        result.remove("live");
                    }
                    Gson gson = new Gson();
                    Carousel carousel1 = gson.fromJson(result.toString(), Carousel.class);
                    List<BannerDataInfo> carousel = carousel1.Carousel;
                    mBannerList.clear();
                    mBannerList.addAll(carousel);
                    if (carousel1.live != null) {
                        List<LiveDataInfo> live = carousel1.live;
                        mLiveData.clear();
                        mLiveData.addAll(live);
                    }
                    if (mBaseList != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onDataBackWithLoadType(int whichApi, int loadType, Object... pObjects) {
        if (whichApi == ApiConfig.MAIN_PAGE_LIST) {
            mBaseList = (BaseInfo<List<MainPageListInfo>>) pObjects[0];
            if (mBaseList.isSuccess()) {
                if (loadType == LoadType.REFRESH) {
                    mListData.clear();
                    mRefreshLayout.finishRefresh();
                } else if (loadType == LoadType.MORE) mRefreshLayout.finishLoadMore();
                List<MainPageListInfo> result = mBaseList.result;
                mListData.addAll(result);
                if (mBannerList.size() != 0) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
