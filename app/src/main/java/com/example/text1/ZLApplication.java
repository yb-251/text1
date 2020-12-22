package com.example.text1;

import android.content.Context;

import com.example.frame10.frame.FrameApplication;

public class ZLApplication extends FrameApplication {

    private static ZLApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static ZLApplication getApplication(){
        return mApplication;
    }
    public static Context getMyApplicationContext(){
        return mApplication.getApplicationContext();
    }
}
