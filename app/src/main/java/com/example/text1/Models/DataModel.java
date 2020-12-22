package com.example.text1.Models;


import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;
import com.example.text1.R;
import com.example.text1.ZLApplication;

public class DataModel implements ICommonModel {
    private NetManager mManager = NetManager.getNetManager();

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object[] pObjects) {

    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {
        switch (whichApi) {
            case ApiConfig.DATA_GROUP_LIST:
                ParamMap map = ParamMap.add("type", 1).add("page", pObjects[0]).add("fid", pObjects[1]);
                mManager.netWork(NetManager.getNetService(ZLApplication.getMyApplicationContext().getString(R.string.bbs_openapi)).getDataGroupList(map), pPresenter, whichApi, loadType);
                break;
            case ApiConfig.DATA_BEST_LIST:
                ParamMap map1 = ParamMap.add("page", pObjects[0]).add("fid", pObjects[1]);
                mManager.netWork(NetManager.getNetService(ZLApplication.getMyApplicationContext().getString(R.string.bbs_openapi)).getDataBestList(map1), pPresenter, whichApi, loadType);
                break;
        }
    }
}
