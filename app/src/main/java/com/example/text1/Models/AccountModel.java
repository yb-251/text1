package com.example.text1.Models;

import android.content.Context;

import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.FrameApplication;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;
import com.example.frame10.secret.RsaUtil;
import com.example.text1.R;
import com.example.text1.ZLApplication;

public class AccountModel implements ICommonModel {
    private NetManager manager = NetManager.getNetManager();
    private Context mContext = FrameApplication.getFrameApplicationContext();

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object[] pObjects) {
        switch (whichApi) {
            case ApiConfig.CHECK_PHONE_IS_USED:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_api)).checkMobileIsUse(pObjects[0]), pPresenter, whichApi);
                break;
            case ApiConfig.REGISTER_SEND_CODE:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_api)).sendMobileCode(pObjects[0]), pPresenter, whichApi);
                break;
            case ApiConfig.CHECK_VERIFY:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_api)).checkMobileCode(pObjects[0], pObjects[1]), pPresenter, whichApi);
                break;
            case ApiConfig.CHECK_USER_NAME:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport)).checkName(pObjects[0]), pPresenter, whichApi);
                break;
            case ApiConfig.BIND_USER_INFO:
                ParamMap maps = (ParamMap) pObjects[0];
                maps.add("specialty_id", ZLApplication.getApplication().subjectId).add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                manager.netWorkByObserver(manager.getNetService(mContext.getString(R.string.passport_api)).bindUserName(maps), pPresenter, whichApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
                ParamMap map = ParamMap.add("ZLSessionID", "").add("seccode", "").add("loginName", pObjects[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) pObjects[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_openapi)).loginAccount(map), pPresenter, whichApi);
                break;
            case ApiConfig.USER_INFO:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_api)).getUserHeaderForMobile(), pPresenter, whichApi);
                break;
            case ApiConfig.LOGIN_GET_VERIFY:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_openapi_user)).getLoginMobile((String) pObjects[0]),pPresenter,whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                manager.netWork(manager.getNetService(mContext.getString(R.string.passport_openapi_user)).loginByMobile(ParamMap.add("mobile",pObjects[0]).add("code",pObjects[1])),pPresenter,whichApi);
                break;
        }
    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {

    }
}
