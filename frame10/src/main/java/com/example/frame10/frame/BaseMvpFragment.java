package com.example.frame10.frame;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<M extends ICommonModel> extends BaseFragment implements ICommonView {
    public CommonPresenter mPresenter;
    private M mModel;
    private Unbinder mBind;
    public int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(setLayout(), container, false);
        mBind = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mModel = setModel();
        mPresenter = new CommonPresenter(this, mModel);
        setUpView();
        setUpData();
    }

    @Override
    public void onDestroyView() {
        //在view销毁的时候，解除和Presenter的绑定
        mPresenter.finishConnected();
        super.onDestroyView();
        if (mBind != null)mBind.unbind();
    }

    public abstract int setLayout();

    /**
     * 每个页面都会初始化控件，我们为避免oncreate臃肿，一般会将这些内容写到一个方法中，如果每次创建方法，比较
     * 麻烦，所以将该方法放到base类中，通过抽象修饰，当子类继承父类后，重写该方法，而该方法在父类的oncreate中
     * 进行了调用，子类虽然没有覆写oncreate方法，但会默认执行，当执行到内部super的时候，会调用父类的oncreate
     * 方法，于是，该方法在子类中被执行了。
     */
    public abstract void setUpView();

    /**
     * 同上
     */
    public abstract void setUpData();

    /**
     * 该方法用于声明view对应的model层
     * 执行逻辑同setUpView
     * 在该框架中，presenter是静态的，而我们的model层随着业务的变化会发生变化，所以当共Presenter在拉起model
     * 执行网络请求时，并不能明确知道要通过创建哪个model对象来进行网络请求，所以我们将model的对象创建放在
     * view层，将该实例通过presenter的构造传入presenter层使用
     * @return 该泛型是公共model接口的子实现类，我们可以在view层动态执行其具体子类型
     */
    public abstract M setModel();

    /**
     * presenter回调的结果会执行该类中onSuccess方法，后期可能对于请求成功的一些公共逻辑在框架层处理，所以在
     * 该类中重写了onSuccess方法，而父类重写后，子类并不会强制重写，所以定义该抽象方法，用于子类重写，当
     * onSuccess执行时，调用了该方法，触发子类中重写的该方法执行
     * @param whichApi 接口标识，用于区分在一个页面中的多个接口
     * @param pObjects 主要用于存放网络请求成功回传的实体对象
     */
    public abstract void onDataBack(int whichApi, Object... pObjects);

    /**
     * 该方法基本原理同上，不同点：多了一个加载类型的回传。另外之所以并未将其定义为抽象方法，是因为并不是在每个子
     * 类中，都涉及到刷新和加载的问题，所以不强制子类重写，如果有需求，可以手动重写。
     * @param whichApi
     * @param loadType
     * @param pObjects
     * 这个方法其实可以不存在，因为既然是手动重写，那可以直接重写调用该方法的回调方法（记住不要骂我sb）
     */
    public void onDataBackWithLoadType(int whichApi,int loadType, Object... pObjects){

    }

    /**
     * 该抽象方法用于向子类回调 没有 加载类型的结果
     * 之所以将其定义为object的可变参数，是因为在特殊需求环境下，接口回传的内容可能不只是网络成功的实体对象
     * @param whichApi 接口标识，用于区分在一个页面中的多个接口
     * @param pObjects 主要用于存放网络请求成功回传的实体对象
     */
    @Override
    public void onSuccess(int whichApi, Object[] pObjects) {
        showLog("netWork finish , whichApi:" + whichApi);
        onDataBack(whichApi,pObjects);
    }

    /**
     * 该抽象方法用于向子类回调 有 加载类型的结果
     * 之所以将其定义为object的可变参数，是因为在特殊需求环境下，接口回传的内容可能不只是网络成功的实体对象
     * @param whichApi 接口标识，用于区分在一个页面中的多个接口
     * @param loadType 该表示用于区分列表接口的加载类型，比如正常加载、加载更多、刷新数据，具体规则请参照该框架汇总的LoadType类
     * @param pObjects 主要用于存放网络请求成功回传的实体对象
     */
    @Override
    public void onSuccessWithLoadType(int whichApi, int loadType, Object[] pObjects) {
        showLog("netWork finish , whichApi:" + whichApi);
        onDataBackWithLoadType(whichApi,loadType,pObjects);
    }

    /**
     * 此方法是presenter回调的失败结果，一般情况下子类并不会重写该方法，我们将错误类型进行了统一的打印，后期也
     * 可能会做其他的处理，如果子类中需要监听具体的失败情况处理自身逻辑，可以手动重写该方法
     * @param whichApi
     * @param pThrowable 回传的错误对象
     */
    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        showLog("netWork error , error content : " + pThrowable != null && !TextUtils.isEmpty(pThrowable.getMessage()) ? pThrowable.getMessage() : "unKnow type");
    }
}
