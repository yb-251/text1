package com.example.frame10.frame;

public interface ICommonView<T> {
    void onSuccess(int whichApi,T... pTS);
    void onSuccessWithLoadType(int whichApi,int loadType,T... pTS);
    void onFailed(int whichApi,Throwable pThrowable);
}
