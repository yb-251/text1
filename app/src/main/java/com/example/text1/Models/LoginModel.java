package com.example.text1.Models;

import android.content.Context;
import android.text.TextUtils;

import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.IService;
import com.example.frame10.frame.NetManager;
import com.example.text1.R;
import com.example.text1.ZLApplication;

public class LoginModel implements ICommonModel {
    private NetManager mManager = NetManager.getNetManager();
    private Context mContext = ZLApplication.getMyApplicationContext();

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object[] p) {
        switch (whichApi) {
            case ApiConfig.GET_ADVERT:
                IService advert = NetManager.getNetService(mContext.getString(R.string.ad_openapi));
                ParamMap add = ParamMap.add("w", p[1]).add("h", p[2]).add("is_show", 0).add("positions_id", "APP_QD_01");
                add.add("specialty_id", !TextUtils.isEmpty((String) p[0]) ? p[0] : 9);
                mManager.netWork(advert.getAdvertInfo(add), pPresenter, whichApi);
                break;
            case ApiConfig.SUBJECT:
                mManager.netWork(NetManager.getNetService(mContext.getString(R.string.edu_openapi)).getSubject(), pPresenter, whichApi);
                break;
        }
    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {

    }
}
