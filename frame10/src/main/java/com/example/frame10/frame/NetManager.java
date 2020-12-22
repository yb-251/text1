package com.example.frame10.frame;

import com.example.frame10.constants.LoadType;
import com.example.frame10.constants.UrlConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    //保证线程的可见性和有序性
    private static volatile NetManager sNetManager;
    public static IService mIService;

    private NetManager() {
    }

    public static NetManager getNetManager() {
        //判断如果对象不为空时，不要再持有锁
        if (sNetManager == null) {
            //保证多线程同时访问时的线程安全问题
            synchronized (NetManager.class) {
                if (sNetManager == null) {
                    sNetManager = new NetManager();
                    mIService = getNetService();
                }
            }
        }
        return sNetManager;
    }

    /**
     * 如果使用框架所规定的域名，请使用mIService对象，不要调用该方法，该方法测试时动态切换baseURL
     * @param pPS 传入的接口域名
     * @return
     */
    public static IService getNetService(String... pPS) {
        String baseUrl = pPS == null || pPS.length == 0 ? UrlConstant.BASE_URL : pPS[0];
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
                .create(IService.class);
    }

    private static OkHttpClient initClient() {
        return new OkHttpClient().newBuilder()
//                .addInterceptor(new CommonHeadersInterceptor())
//                .addInterceptor(new CommonParamsInterceptor())
                .addInterceptor(initLogInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * log日志拦截器
     * @return
     */
    private static Interceptor initLogInterceptor() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        return log;
    }

    public void netWork(Observable pObservable, final ICommonPresenter pPresenter, final int whichApi) {
        netWork(pObservable, pPresenter, whichApi, LoadType.NONE);
    }

    public void netWork(Observable pObservable, final ICommonPresenter pPresenter, final int whichApi, final int loadType) {
        Disposable subscribe = pObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(info -> {
                            if (loadType == LoadType.NONE)
                                pPresenter.onSuccess(whichApi, info);
                            else pPresenter.onSuccessWithLoadType(whichApi, loadType, info);
                        },
                        error -> pPresenter.onFailed(whichApi, (Throwable) error));
        ((CommonPresenter) pPresenter).mDisposables.add(subscribe);
    }


    public void netWorkByObserver(Observable pObservable, final ICommonPresenter pPresenter, final int whichApi) {
        netWorkByObserver(pObservable,pPresenter,whichApi,LoadType.NONE);
    }

    public void netWorkByObserver(Observable pObservable, final ICommonPresenter pPresenter, final int whichApi, final int loadType) {
        pObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ((CommonPresenter) pPresenter).mDisposables.add(d);
                    }

                    @Override
                    public void onNext(Object value) {
                        if (loadType == LoadType.NONE)
                            pPresenter.onSuccess(whichApi, value);
                        else pPresenter.onSuccessWithLoadType(whichApi, loadType, value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        pPresenter.onFailed(whichApi, e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
