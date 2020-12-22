package com.example.frame10.frame;

public interface ICommonPresenter<P> extends ICommonView {
    void getData(int whichApi,P... pPS);
    default void getDataWithLoadType(int whichApi,int loadType,P... pPS){}
}
