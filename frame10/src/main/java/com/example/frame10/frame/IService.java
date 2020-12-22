package com.example.frame10.frame;

import com.example.datalibrary.AdvertInfo;
import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.DataListInfo;
import com.example.datalibrary.DemoInfo;
import com.example.datalibrary.LoginInfo;
import com.example.datalibrary.MainPageListInfo;
import com.example.datalibrary.PersonHeader;
import com.example.datalibrary.SubjectInfo;
import com.example.datalibrary.VipBannerAndLiveInfo;
import com.example.datalibrary.VipListInfo;
import com.google.gson.JsonObject;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IService {
    @GET(".")
    Observable<DemoInfo> getDemoData(@QueryMap Map<String, Object> mParams);

    @GET("loginByMobileCode")
    Observable<BaseInfo> getVerifyCode(@QueryMap Map<String, Object> mParams);

    @GET("ad/getAd")
    Observable<BaseInfo<AdvertInfo>> getAdvertInfo(@QueryMap Map<String, Object> mParams);

    @GET("lesson/getAllspecialty")
    Observable<BaseInfo<List<SubjectInfo>>> getSubject();

    @GET("getCarouselphoto")
    Observable<JsonObject> getBannerData(@QueryMap Map<String, Object> params);

    @GET("getIndexCommend")
    Observable<BaseInfo<List<MainPageListInfo>>> getMainList(@QueryMap Map<String, Object> params);

    @GET("lesson/getLessonListForApi")
    Observable<JsonObject> getCourseChildList(@QueryMap Map<String, Object> params);

    @GET("group/getGroupList")
    Observable<BaseInfo<List<DataListInfo>>> getDataGroupList(@QueryMap Map<String, Object> params);

    @GET("group/getThreadEssence")
    Observable<BaseInfo<List<DataListInfo>>> getDataBestList(@QueryMap Map<String, Object> params);

    @POST("checkMobileIsUse")
    @FormUrlEncoded
    Observable<BaseInfo> checkMobileIsUse(@Field("mobile") Object mobile);

    @POST("sendMobileCode")
    @FormUrlEncoded
    Observable<BaseInfo> sendMobileCode(@Field("mobile") Object mobile);

    @POST("checkMobileCode")
    @FormUrlEncoded
    Observable<BaseInfo> checkMobileCode(@Field("mobile") Object mobile, @Field("code") Object code);

    @POST("user/usernameIsExist")
    @FormUrlEncoded
    Observable<BaseInfo> checkName(@Field("username") Object mobile);

    @POST("userRegForSimple")
    @FormUrlEncoded
    Observable<BaseInfo> bindUserName(@FieldMap Map<String, Object> params);

    @POST("user/userLoginNewAuth")
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginAccount(@FieldMap Map<String, Object> params);

    @POST("getUserHeaderForMobile")
    Observable<BaseInfo<PersonHeader>> getUserHeaderForMobile();

    @POST("joingroup")
    @FormUrlEncoded
    Observable<BaseInfo> focus(@FieldMap Map<String, Object> params);

    @POST("removeGroup")
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@FieldMap Map<String, Object> params);

    @GET("loginByMobileCode")
    Observable<BaseInfo> getLoginMobile(@Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByMobile(@QueryMap Map<String, Object> params);

    @GET("get_new_vip")
    Observable<BaseInfo<VipBannerAndLiveInfo>> get_new_vip(@Query("pro") String mobile);

    @GET("getVipSmallLessonList")
    Observable<BaseInfo<VipListInfo>> getVipSmallLessonList(@QueryMap Map<String, Object> params);
}
