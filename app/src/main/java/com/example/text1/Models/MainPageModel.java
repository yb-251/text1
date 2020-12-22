package com.example.text1.Models;

import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;
import com.example.text1.SPConstant.Constant;
import com.example.text1.ZLApplication;

public class MainPageModel implements ICommonModel {

    private NetManager mManager = NetManager.getNetManager();
    private String baseUrl = "https://edu.zhulong.com/openapi/lesson/";

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object... pObjects) {
        switch (whichApi) {
            case ApiConfig.BANNER_DATA:
                mManager.netWork(NetManager.getNetService(baseUrl).getBannerData(ParamMap.add("pro", 9).add("more_live", 1).add("is_new", 1).add("new_banner", 1)), pPresenter, whichApi);
                break;
        }
    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {
        switch (whichApi) {
            case ApiConfig.MAIN_PAGE_LIST:
                mManager.netWork(NetManager.getNetService(baseUrl).getMainList(ParamMap.add("specialty_id", ZLApplication.getApplication().subjectId).add("page", pObjects[0]).add("limit", Constant.LIST_LIMIT).add("new_banner", 1)), pPresenter, whichApi, loadType);
                break;
        }
    }
}
