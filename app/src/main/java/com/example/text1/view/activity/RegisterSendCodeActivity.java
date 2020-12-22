package com.example.text1.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.datalibrary.BaseInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.BaseMvpActivity;
import com.example.pop.design.DialogPopup;
import com.example.text1.Models.AccountModel;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.view.customs.MyTextWatcher;
import com.example.utils.ext.RegularNewUtils;
import com.example.utils.newAdd.SoftInputControl;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterSendCodeActivity extends BaseMvpActivity<AccountModel> implements DialogPopup.DialogClickCallBack {

    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.get_verify)
    TextView getVerify;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    private DialogPopup mDialogPopup;
    private Disposable mSubscribe;


    @Override
    public int setLayout() {
        return R.layout.activity_register_send_code;
    }

    @Override
    public void setUpView() {
        verifyCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void whenTextChanged(CharSequence s, int start, int before, int count) {
                login.setEnabled(s.length() == 6 ? true : false);
            }
        });
    }

    @Override
    public void setUpData() {
        titleContent.setText("手机号注册");
    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    private final int READ_TIME = 59;

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {
        switch (whichApi) {
            case ApiConfig.CHECK_PHONE_IS_USED:
                BaseInfo info = (BaseInfo) pObjects[0];
                if (info.isSuccess()) {
                    if (mDialogPopup == null) {
                        mDialogPopup = new DialogPopup(this, true);
                        mDialogPopup.setBackPressEnable(false);
                        mDialogPopup.setDismissWhenTouchOutside(false);
                        mDialogPopup.setDialogClickCallBack(this);
                    }
                    mDialogPopup.setContent(phoneEt.getText().toString() + "\n" + "是否将短信发送到该手机");
                    mDialogPopup.showPopupWindow();
                } else {
                    showToast(info.msg);
                }
                break;
            case ApiConfig.REGISTER_SEND_CODE:
                BaseInfo sendCodeResult = (BaseInfo) pObjects[0];
                mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(l -> {
                    getVerify.setText(READ_TIME - l > 0 ? READ_TIME - l + "s" : "重新获取");
                    if (READ_TIME - l == 0)
                        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
                });
                break;
            case ApiConfig.CHECK_VERIFY:
                BaseInfo checkVerifyResult = (BaseInfo) pObjects[0];
                if (checkVerifyResult.isSuccess()) {
                    startActivity(new Intent(this, CreateAccountActivity.class).putExtra(Constant.PHONE, areaCode.getText() + phoneEt.getText().toString().trim()));
                    finish();
                } else showToast(checkVerifyResult.msg);
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }

    @OnClick({R.id.left_back, R.id.get_verify, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.get_verify:
                if (RegularNewUtils.isMobileExact(phoneEt.getText().toString().trim())) {
                    SoftInputControl.hideSoftInput(this, phoneEt);
                    mPresenter.getData(ApiConfig.CHECK_PHONE_IS_USED, areaCode.getText() + phoneEt.getText().toString().trim());
                } else showToast("手机格式不正确");
                break;
            case R.id.login:
                mPresenter.getData(ApiConfig.CHECK_VERIFY, areaCode.getText() + phoneEt.getText().toString().trim(), verifyCode.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onClick(int type) {
        if (type == mDialogPopup.OK) {
            mPresenter.getData(ApiConfig.REGISTER_SEND_CODE, areaCode.getText() + phoneEt.getText().toString().trim());
        }
        mDialogPopup.dismiss();
    }
}
