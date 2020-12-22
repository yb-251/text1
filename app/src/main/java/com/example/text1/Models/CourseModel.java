package com.example.text1.Models;


import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.frame.ICommonModel;
import com.example.frame10.frame.ICommonPresenter;
import com.example.frame10.frame.NetManager;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.ZLApplication;

/**
 * Created by 任小龙 on 2020/10/7.
 */
public class CourseModel implements ICommonModel {

    private NetManager mManager = NetManager.getNetManager();

    @Override
    public void getData(int whichApi, ICommonPresenter pPresenter, Object[] pObjects) {

    }

    @Override
    public void getDataWithLoadType(int whichApi, int loadType, ICommonPresenter pPresenter, Object[] pObjects) {
        ParamMap add = ParamMap.add("specialty_id", ZLApplication.getApplication().subjectId).add("page", pObjects[1]).add("limit", Constant.LIST_LIMIT).add("course_type", pObjects[0]);
        mManager.netWork(mManager.getNetService(ZLApplication.getApplication().getString(R.string.edu_openapi)).getCourseChildList(add),pPresenter,whichApi,loadType);
    }
}
