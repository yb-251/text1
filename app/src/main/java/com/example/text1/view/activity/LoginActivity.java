package com.example.text1.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.frame10.frame.BaseMvpActivity;
import com.example.text1.Models.AccountModel;
import com.example.text1.R;
import com.example.text1.view.customs.LoginTabView;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginTabView.LoginTabResultListener {

    @BindView(R.id.login_view)
    LoginTabView loginView;
    @BindView(R.id.check_read)
    ImageView checkRead;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setUpView() {
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);
        loginView.setLoginTabResultListener(this);
    }

    @Override
    public void setUpData() {

    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }

    @Override
    public void getVerify() {

    }

    @Override
    public void register() {
        startActivity(new Intent(this,RegisterSendCodeActivity.class));
    }

    @Override
    public void forgotPwd() {

    }

    @Override
    public void login(int loginType, String account, String pwd) {

    }

    @OnClick({R.id.close_image, R.id.qq_image, R.id.wx_image, R.id.check_read, R.id.service, R.id.privated})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_image:
                finish();
                break;
            case R.id.qq_image:
                break;
            case R.id.wx_image:
                break;
            case R.id.check_read:
                break;
            case R.id.service:
                startActivity(new Intent(this, NoticeActivity.class).putExtra("url", "https://passport.zhulong.com/wap-fuwu.html"));
                break;
            case R.id.privated:
                startActivity(new Intent(this, NoticeActivity.class).putExtra("url", "https://passport.zhulong.com/wap-clause.html"));
                break;
        }
    }
}
