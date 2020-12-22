package com.example.text1.Models;


import android.content.Context;

import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.FrameApplication;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;


public class VipModel implements ICommonModel {
    private NetManager mManager = NetManager.getNetManager();
    private Context mContext = FrameApplication.getFrameApplicationContext();

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object[] pObjects) {

    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {
        switch (whichApi) {
            case ApiConfig.VIP_TOP_DATA:
                mManager.netWork(mManager.getNetService("https://edu.zhulong.com/openapi/lesson/").get_new_vip(FrameApplication.getFrameApplication().subjectId),pPresenter,whichApi,loadType);
                break;
            case ApiConfig.VIP_LIST_DATA:
                ParamMap map = ParamMap.add("specialty_id",FrameApplication.getFrameApplication().subjectId).add("sort",1).add("page",pObjects[0]);
                mManager.netWork(mManager.getNetService("https://edu.zhulong.com/openapi/lesson/").getVipSmallLessonList(map),pPresenter,whichApi,loadType);
                break;
        }
    }
}
