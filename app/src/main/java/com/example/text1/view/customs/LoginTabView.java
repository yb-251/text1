package com.example.text1.view.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.text1.R;
import com.example.utils.ext.RegularNewUtils;
import com.example.utils.ext.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginTabView extends RelativeLayout {
    private final int mShowMode;
    @BindView(R.id.account_login)
    TextView accountLogin;
    @BindView(R.id.verify_login)
    TextView verifyLogin;
    @BindView(R.id.indicator_account)
    View indicatorAccount;
    @BindView(R.id.indicator_verify)
    View indicatorVerify;
    @BindView(R.id.clear_view)
    ImageView clearView;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.show_secret)
    ImageView showSecret;
    @BindView(R.id.account_input_view)
    ConstraintLayout accountGroup;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.get_verify)
    public TextView getVerify;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.login)
    public TextView login;
    @BindView(R.id.verify_group)
    ConstraintLayout verifyGroup;
    @BindView(R.id.account_secret)
    EditText mAccountPWD;

    private final int ACCOUNT = 1, VERIFY = 2;
    private int mCurrentTab = ACCOUNT;

    public LoginTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginTabView, 0, 0);
        mShowMode = ta.getInt(R.styleable.LoginTabView_mode, 2);
        initView(context);
        ta.recycle();
    }

    private void initView(Context pContext) {
        View view = LayoutInflater.from(pContext).inflate(R.layout.login_center_control_layout, this);
        ButterKnife.bind(this, view);
        accountName.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void whenTextChanged(CharSequence pCharSequence, int start, int before, int count) {
                clearView.setVisibility(pCharSequence.length() > 0 ? VISIBLE : INVISIBLE);
            }
        });
        if (mShowMode == 1) {//只有账号登录
            goneTab();
            verifyGroup.setVisibility(INVISIBLE);
        } else if (mShowMode == 3) {
            goneTab();
            verifyGroup.setVisibility(VISIBLE);
            accountGroup.setVisibility(INVISIBLE);
        }
    }

    private void goneTab() {
        accountName.setVisibility(GONE);
        indicatorAccount.setVisibility(GONE);
        verifyLogin.setVisibility(GONE);
        indicatorVerify.setVisibility(GONE);
    }

    @OnClick({R.id.account_login, R.id.verify_login, R.id.clear_view, R.id.show_secret, R.id.get_verify, R.id.login, R.id.register, R.id.forgot_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_login:
                if (mCurrentTab == ACCOUNT) return;
                accountLogin.setTextColor(ContextCompat.getColor(getContext(), R.color.appThemeRed));
                indicatorAccount.setVisibility(VISIBLE);
                accountGroup.setVisibility(VISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(getContext(), R.color.font_color_dark));
                indicatorVerify.setVisibility(INVISIBLE);
                verifyGroup.setVisibility(INVISIBLE);
                mCurrentTab = ACCOUNT;
                break;
            case R.id.verify_login:
                if (mCurrentTab == VERIFY) return;
                accountLogin.setTextColor(ContextCompat.getColor(getContext(), R.color.font_color_dark));
                indicatorAccount.setVisibility(INVISIBLE);
                accountGroup.setVisibility(INVISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(getContext(), R.color.appThemeRed));
                indicatorVerify.setVisibility(VISIBLE);
                verifyGroup.setVisibility(VISIBLE);
                mCurrentTab = VERIFY;
                break;
            case R.id.clear_view:
                accountName.setText("");
                break;
            case R.id.show_secret:
                mAccountPWD.setTransformationMethod(showSecret.isSelected() ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
                showSecret.setSelected(!showSecret.isSelected());
                break;
            case R.id.get_verify:
                if (mLoginTabResultListener != null) mLoginTabResultListener.getVerify();
                break;
            case R.id.login:
                if (mLoginTabResultListener != null) {
                    String name = "", pwd = "";
                    if (mCurrentTab == ACCOUNT) {
                        name = accountName.getText().toString().trim();
                        pwd = mAccountPWD.getText().toString().trim();
                    } else {
                        String phone = phoneEt.getText().toString().trim();
                        if (!RegularNewUtils.isMobileExact(phone)) {
                            ToastUtils.show(getContext(), "手机号格式错误");
                            return;
                        }
                        name = areaCode.getText().toString().trim() + phone;
                        pwd = verifyCode.getText().toString().trim();
                    }
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                        Toast.makeText(getContext(), "账号和密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    mLoginTabResultListener.login(mCurrentTab, name, pwd);
                }
                break;
            case R.id.register:
                if (mLoginTabResultListener != null) mLoginTabResultListener.register();
                break;
            case R.id.forgot_pwd:
                if (mLoginTabResultListener != null) mLoginTabResultListener.forgotPwd();
                break;
        }
    }

    private LoginTabResultListener mLoginTabResultListener;

    public void setLoginTabResultListener(LoginTabResultListener pLoginTabResultListener) {
        mLoginTabResultListener = pLoginTabResultListener;
    }

    public interface LoginTabResultListener {
        void getVerify();

        void register();

        void forgotPwd();

        void login(int loginType, String account, String pwd);
    }
}
