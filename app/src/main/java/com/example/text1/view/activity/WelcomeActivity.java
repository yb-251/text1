package com.example.text1.view.activity;

import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datalibrary.AdvertInfo;
import com.example.datalibrary.BaseInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.BaseMvpActivity;
import com.example.text1.Models.LoginModel;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.SPConstant.SPConstant;
import com.example.utils.newAdd.GlideUtil;
import com.example.utils.newAdd.SharedPrefrenceUtils;
import com.example.utils.newAdd.SystemUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends BaseMvpActivity<LoginModel> {

    @BindView(R.id.advert_image)
    ImageView advertImage;
    @BindView(R.id.advert_time)
    TextView advertTime;

    @Override
    public int setLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void setUpView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void setUpData() {
        Point realSize = SystemUtils.getRealSize(this);
        mFrameApplication.subjectId = SharedPrefrenceUtils.getString(this, SPConstant.SUBJECT_ID, "");
        mPresenter.getData(ApiConfig.GET_ADVERT, mFrameApplication.subjectId, realSize.x, realSize.y);
    }

    @Override
    public LoginModel setModel() {
        return new LoginModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {
        BaseInfo<AdvertInfo> baseInfo = (BaseInfo<AdvertInfo>) pObjects[0];
        if (baseInfo.isSuccess()) {
            AdvertInfo advertInfo = baseInfo.result;
            GlideUtil.loadImage(advertImage, advertInfo.info_url);
            advertTime.setVisibility(View.VISIBLE);
            readTime();
            mFrameApplication.subjectList = SharedPrefrenceUtils.getSerializableList(this, SPConstant.SUBJECT_LIST);
        }
    }

    private int preTime = 5;
    private Disposable subscribe;

    private void readTime() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(pLong -> {
                    if (preTime - pLong > 0)
                        advertTime.setText(preTime - pLong + "s");
                    else {
                        timeDispose();
                        getOut();
                    }
                });
    }

    private void getOut() {
        Intent intent = new Intent(this, TextUtils.isEmpty(mFrameApplication.subjectId) ? ChooseSpecialActivity.class : HomeActivity.class);
        intent.putExtra(Constant.COME_FROM, "splash");
        startActivity(intent);
        finish();
    }

    private void timeDispose() {
        if (subscribe != null && !subscribe.isDisposed()) subscribe.dispose();
    }

    @Override
    protected void onDestroy() {
        timeDispose();
        super.onDestroy();
    }

    @OnClick(R.id.advert_time)
    public void onViewClicked() {
        Observable.just("").debounce(50, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    timeDispose();
                    getOut();
                });
    }

    @Override
    public void onBackPressed() {
    }
}
