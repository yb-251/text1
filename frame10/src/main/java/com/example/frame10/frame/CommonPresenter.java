package com.example.frame10.frame;


import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommonPresenter<V extends ICommonView,M extends ICommonModel> implements ICommonPresenter {
    private WeakReference<V> mView;
    private WeakReference<M> mModel;
    public List<Disposable> mDisposables;

    /**
     * 用于view创建presenter的构造，因为presenter要回调数据给view层，调用model的对象发起网络任务，所以这里通过
     * 泛型指定了view和model的类型为框架抽取的公共接口的子实现类
     * WeakReference：如果应用长时间使用或快速切换页面过程中，view视图不断创建，那么presenter和model也会同步创建对象，如果gc
     * 线程不能及时触发该实例，导致由于频繁创建对象回收不及时内存陡增，通过弱引用包裹presenter和model的实例，加快其
     * 回收的速度
     * mDisposables：当对应view的网络任务开始后，如果view在网络请求完成前销毁，那model请求的数据也就没有继续
     * 请求的必要了，通过获取model的disposable对象，在presenter层统一添加到集合当中，绑定和view的生命周期，当view销毁
     * 停止所有网络任务，这里主要是当presenter对象创建时，对应的创建存储当前view所有网络请求的绑定对象。
     * @param pView view对象
     * @param pModel model对象
     */
    public CommonPresenter(V pView, M pModel) {
        mView = new WeakReference<>(pView);
        mModel = new WeakReference<>(pModel);
        if (mDisposables == null)mDisposables = new ArrayList<>();
    }

    /**
     * 该方法用于view通过presenter的引用，调用其发起网络请求
     * @param whichApi 接口标识
     * @param pObjects 一般是接口参数，如果有其他参数的需求，可以动态添加到该可变参数中
     */
    @Override
    public void getData(int whichApi, Object... pObjects) {
        if (mModel != null && mModel.get() != null) mModel.get().getData(whichApi,this, pObjects);
        else Log.e(this.getClass().getSimpleName(), "getData: model maybe null" );
    }

    /**
     * 作用同上，增加了加载类型标识，一般用于带刷新和加载更多的网络任务
     * @param whichApi 接口标识
     * @param loadType 加载类型标识，具体情况参照LoadType
     * @param pObjects 一般是接口参数，如果有其他参数的需求，可以动态添加到该可变参数中
     */
    @Override
    public void getDataWithLoadType(int whichApi, int loadType, Object... pObjects) {
        if (mModel != null && mModel.get() != null) mModel.get().getDataWithLoadType(whichApi,loadType,this, pObjects);
        else Log.e(this.getClass().getSimpleName(), "getData: model maybe null" );
    }

    /**
     * 网络任务完成回调的方法，该方法
     * @param whichApi 回传的接口标识，明确当前哪个网络任务完成了
     * @param pObjects 网络请求成功后回传的实体对象获取在特殊环境下需回传的一些特殊标识
     */
    @Override
    public void onSuccess(int whichApi, Object... pObjects) {
        if (mView != null && mView.get() != null) mView.get().onSuccess(whichApi, pObjects);
    }

    /**
     * 同上，增加加载类型
     * @param whichApi 回传的接口标识，明确当前哪个网络任务完成了
     * @param loadType 告诉view层当前返回的任务是加载更多还是刷新，以针对性的控制集合数据和取消页面刷新加载的效果
     * @param pObjects 网络请求成功后回传的实体对象获取在特殊环境下需回传的一些特殊标识
     */
    @Override
    public void onSuccessWithLoadType(int whichApi, int loadType, Object[] pObjects) {
        if (mView != null && mView.get() != null) mView.get().onSuccessWithLoadType(whichApi,loadType, pObjects);
    }

    /**
     * 网络请求失败的回调
     * @param whichApi 失败的接口标识
     * @param pThrowable 失败返回的throwable对象
     */
    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        if (mView != null && mView.get() != null) mView.get().onFailed(whichApi, pThrowable);
    }

    /**
     * 该方法用于在view销毁时调用，具体内容有以下几点
     * 1，当view销毁时，断开所有该view发起的网络任务
     * 2，接触presenter和view、model的绑定关系
     */
    public void finishConnected(){
        if (mDisposables != null && mDisposables.size() != 0){
            for (Disposable disposable:mDisposables) {
                if (disposable != null && !disposable.isDisposed())disposable.dispose();
            }
        }
        mDisposables.clear();
        mDisposables = null;
        if (mView != null)mView.clear();
        if (mModel != null)mModel.clear();
        mView = null;
        mModel = null;
    }
}
