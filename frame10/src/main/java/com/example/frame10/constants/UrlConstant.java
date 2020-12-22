package com.example.frame10.constants;

public class UrlConstant {
    private static final int INNER_NET=1,TEST_OUTER_NET=2,RELEASE_ENT=3;
    private static int type = RELEASE_ENT;
    public static String BASE_URL = "";
    static {
        if (type == RELEASE_ENT){
            BASE_URL = "http://static.owspace.com/";
        }else if (type == TEST_OUTER_NET){
            BASE_URL = "";
        }else {
            BASE_URL = "";
        }
    }

    public static final String ZHULONG1 = "https://passport.zhulong.com/openapi/user/";
}
