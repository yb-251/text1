package com.example.frame10.frame;

import android.content.Context;

import com.example.datalibrary.Device;
import com.example.datalibrary.LoginInfo;
import com.example.datalibrary.SubjectInfo;
import com.example.utils.UtilsApplication;

import java.util.List;

public class FrameApplication extends UtilsApplication {
    public String subjectId;
    public List<SubjectInfo> subjectList;
    public Device device;
    private LoginInfo mLoginInfo;
    private static FrameApplication mApplication;

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public void setLoginInfo(LoginInfo pLoginInfo) {
        mLoginInfo = pLoginInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static FrameApplication getFrameApplication(){
        return mApplication;
    }

    public static Context getFrameApplicationContext(){
        return mApplication.getApplicationContext();
    }

}
