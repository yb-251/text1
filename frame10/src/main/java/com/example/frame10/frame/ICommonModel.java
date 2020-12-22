package com.example.frame10.frame;

public interface ICommonModel<M> {
    /**
     * 执行无加载类型的网络任务
     * @param whichApi 当前加载的接口标识
     * @param pPresenter 通过获取该实例以回传成功的数据
     * @param pMS 有些网络参数需要通过view传递过来，不知道数量和类型，定义的可变参数
     */
    void getData(int whichApi,ICommonPresenter pPresenter,M... pMS);
    /**
     * 执行无加载类型的网络任务
     * @param whichApi 当前加载的接口标识
     * @param pPresenter 通过获取该实例以回传成功的数据
     * @param loadType 加载类型
     * @param pMS 有些网络参数需要通过view传递过来，不知道数量和类型，定义的可变参数
     */
    void getDataWithLoadType(int whichApi,int loadType,ICommonPresenter pPresenter,M... pMS);
}
