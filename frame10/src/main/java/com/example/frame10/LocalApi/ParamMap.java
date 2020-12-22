package com.example.frame10.LocalApi;

import java.util.HashMap;

public class ParamMap extends HashMap {

    private static volatile ParamMap mParamMap;

    public static ParamMap add(String key, Object value) {
        if (mParamMap == null){
            synchronized (ParamMap.class){
                if(mParamMap == null){
                    mParamMap = new ParamMap();
                }
            }
        }
        mParamMap.put(key, value);
        return mParamMap;
    }
}
