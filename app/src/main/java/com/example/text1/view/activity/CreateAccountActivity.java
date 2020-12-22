package com.example.text1.view.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datalibrary.BaseInfo;
import com.example.frame10.LocalApi.ParamMap;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.BaseMvpActivity;
import com.example.frame10.secret.RsaUtil;
import com.example.text1.Models.AccountModel;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.SPConstant.SPConstant;
import com.example.text1.tools.CheckUserNameAndPwd;
import com.example.text1.view.customs.MyTextWatcher;
import com.example.utils.newAdd.SharedPrefrenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateAccountActivity extends BaseMvpActivity<AccountModel> {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.clear_view)
    ImageView clearView;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.show_secret)
    ImageView showSecret;
    @BindView(R.id.account_secret)
    EditText accountSecret;
    @BindView(R.id.create_account_next)
    TextView createAccountNext;
    private String mPhone;

    @Override
    public int setLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    public void setUpView() {
        titleContent.setText("设置用户名");
        accountName.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void whenTextChanged(CharSequence s, int start, int before, int count) {
                clearView.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    @Override
    public void setUpData() {
        mPhone = getIntent().getStringExtra(Constant.PHONE);
    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {
        switch (whichApi){
            case ApiConfig.CHECK_USER_NAME:
                BaseInfo nameResult = (BaseInfo) pObjects[0];
                if (nameResult.isSuccess()){
                    ParamMap add = ParamMap.add("username", getContent(accountName)).add("password", RsaUtil.encryptByPublic(getContent(accountSecret))).add("tel", mPhone);
                    mPresenter.getData(ApiConfig.BIND_USER_INFO,add);
                }else showToast(nameResult.msg);
                break;
            case ApiConfig.BIND_USER_INFO://+86133123456780
                BaseInfo bindResult = (BaseInfo) pObjects[0];
                if (bindResult.errNo == 24 || bindResult.errNo == 114 || bindResult.isSuccess()){
                    showToast("注册成功");
                    SharedPrefrenceUtils.saveString(this, SPConstant.USER_NAME,getContent(accountName));
                    String s = (mPhone + "0").substring(3, 14);
                    SharedPrefrenceUtils.saveString(this,SPConstant.PHONE_NUM,s);
                    finish();
                }
                break;
        }
    }



    @OnClick({R.id.left_back, R.id.clear_view, R.id.show_secret, R.id.create_account_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.clear_view:
                accountName.setText("");
                break;
            case R.id.show_secret:
                accountSecret.setTransformationMethod(showSecret.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
                showSecret.setSelected(!showSecret.isSelected());
                break;
            case R.id.create_account_next:
                boolean b = CheckUserNameAndPwd.verificationUsername(this, getContent(accountName), getContent(accountSecret));
                if (b){
                    mPresenter.getData(ApiConfig.CHECK_USER_NAME,getContent(accountName));
                }
                break;
        }
    }
}
