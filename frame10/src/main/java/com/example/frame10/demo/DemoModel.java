package com.example.frame10.demo;

import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.constants.UrlConstant;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;

import java.util.Map;

public class DemoModel implements ICommonModel {
    private NetManager manager = NetManager.getNetManager();

    @Override
    public void getData(final int whichApi, final ICommonPresenter pPresenter, Object[] pObjects) {
        manager.netWork(manager.getNetService(UrlConstant.ZHULONG1).getVerifyCode(ParamMap.add("mobile",pObjects[0])), pPresenter, whichApi);
    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {
        switch (whichApi) {
            case ApiConfig.DEMO_TEST:
                Map<String, Object> param = ParamMap.add("c", "api").add("a", "getList").add("page_id", pObjects[0]);
                manager.netWorkByObserver(manager.mIService.getDemoData(param), pPresenter, whichApi, loadType);
                break;
        }
    }
}
