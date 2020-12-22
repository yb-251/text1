package com.example.text1.view.fragment;

import androidx.fragment.app.Fragment;

import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.VipBannerAndLiveInfo;
import com.example.datalibrary.VipCommonInfo;
import com.example.datalibrary.VipListInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.LoadType;
import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.VipModel;
import com.example.text1.R;
import com.example.text1.adapter.VipLargeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipFragment extends BaseMvpFragment<VipModel> {
    private List<VipCommonInfo> recycleList = new ArrayList<>();
    private VipLargeAdapter mAdapter;

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        initRecycler();
        mAdapter = new VipLargeAdapter(getActivity(), recycleList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getDataWithLoadType(ApiConfig.VIP_TOP_DATA, LoadType.NORMAL);
    }

    @Override
    public void onListRefresh() {
        super.onListRefresh();
        mPresenter.getDataWithLoadType(ApiConfig.VIP_LIST_DATA, LoadType.REFRESH, page);
    }

    @Override
    public void onListLoadMore() {
        super.onListLoadMore();
        mPresenter.getDataWithLoadType(ApiConfig.VIP_LIST_DATA, LoadType.MORE, page);
    }

    @Override
    public VipModel setModel() {
        return new VipModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }

    @Override
    public void onDataBackWithLoadType(int whichApi, int loadType, Object... pObjects) {
        super.onDataBackWithLoadType(whichApi, loadType, pObjects);
        switch (whichApi) {
            case ApiConfig.VIP_LIST_DATA:
                BaseInfo<VipListInfo> base = (BaseInfo<VipListInfo>) pObjects[0];
                if (base.isSuccess()) {
                    VipListInfo result = base.result;
                    List<VipListInfo.VipInnerList> list = result.list;
                    if (loadType == LoadType.MORE) {
                        recycleList.get(0).list.addAll(list);
                    } else
                        recycleList.get(0).list = list;
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.VIP_TOP_DATA:
                BaseInfo<VipBannerAndLiveInfo> base2 = (BaseInfo<VipBannerAndLiveInfo>) pObjects[0];
                if (base2.isSuccess()) {
                    VipBannerAndLiveInfo result = base2.result;
                    List<VipBannerAndLiveInfo.BannerVip> lunbotu = result.lunbotu;
                    List<VipBannerAndLiveInfo.LiveVipListInfo> live = result.live.live;
                    VipCommonInfo vip1 = new VipCommonInfo(lunbotu, live, null);
                    recycleList.add(vip1);
                    mPresenter.getDataWithLoadType(ApiConfig.VIP_LIST_DATA, LoadType.NORMAL, page);
                }
                break;
        }
    }
}
