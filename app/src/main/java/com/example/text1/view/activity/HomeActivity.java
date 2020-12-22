package com.example.text1.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datalibrary.SubjectInfo;
import com.example.frame10.frame.BaseActivity;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.interfaces.OnRecyclerItemClickListener;
import com.example.text1.view.customs.BottomTabLayout;
import com.example.text1.view.fragment.CourseFragment;
import com.example.text1.view.fragment.DataFragment;
import com.example.text1.view.fragment.MainPageFragment;
import com.example.text1.view.fragment.MineFragment;
import com.example.text1.view.fragment.VipFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends BaseActivity implements OnRecyclerItemClickListener {
    private List<String> contentList = new ArrayList<>();
    private List<Integer> selectedList = new ArrayList<>();
    private List<Integer> unSelectedList = new ArrayList<>();
    private final int MAIN_PAGE = 1, COURSE = 2, VIP = 3, DATA = 4, MINE = 5;
    private FragmentManager mManager;
    private TextView mSubject;
    public int fid;
    private Fragment mMainFragment, mCourseFragment, mVipFragment, mDataFragment, mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomTabLayout tabLayout = findViewById(R.id.bottom_tabView);
        Collections.addAll(contentList, getString(R.string.main_page), getString(R.string.course), getString(R.string.VIP), getString(R.string.data), getString(R.string.mine));
        Collections.addAll(selectedList, R.drawable.main_selected, R.drawable.course_selected, R.drawable.vip_selected, R.drawable.data_selected, R.drawable.mine_selected);
        Collections.addAll(unSelectedList, R.drawable.main_page_view, R.drawable.course_view, R.drawable.vip_view, R.drawable.data_view, R.drawable.mine_view);
        String s = tabLayout.setResource(selectedList, unSelectedList, contentList);
        if (!TextUtils.isEmpty(s)) showLog("-------------" + s + "-----------");
        tabLayout.setOnRecyclerItemClickListener(this);
        mManager = getSupportFragmentManager();
        showFragment(tabLayout.getDefaultShow());
        initView();
    }

    private void initView() {
        mSubject = findViewById(R.id.subject_content);
        setSubjectContent();
        mSubject.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseSpecialActivity.class);
            intent.putExtra(Constant.COME_FROM, "home");
            startActivityForResult(intent, Constant.HOME_REQUEST_CHOOSE);
        });
    }

    boolean isFound = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.CHOOSE_BACK_HOME) {
            if (mMainFragment != null)
                ((MainPageFragment) mMainFragment).mRefreshLayout.autoRefresh();
            setSubjectContent();
        }
    }

    private void setSubjectContent() {
        for (SubjectInfo info : mFrameApplication.subjectList) {
            if (!isFound) {
                for (SubjectInfo.SubjectDataInfo innerInfo : info.data) {
                    if (innerInfo.specialty_id.equals(mFrameApplication.subjectId)) {
                        mSubject.setText(innerInfo.specialty_name);
                        fid = innerInfo.fid;
                        isFound = true;
                        break;
                    }
                }
            } else break;
        }
        isFound = false;
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mCourseFragment != null) transaction.hide(mCourseFragment);
        if (mVipFragment != null) transaction.hide(mVipFragment);
        if (mDataFragment != null) transaction.hide(mDataFragment);
        if (mMineFragment != null) transaction.hide(mMineFragment);
    }

    @Override
    public void onItemClick(Object[] pos) {
        showFragment((int) pos[0]);
    }

    private void showFragment(int whichFragment) {
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragment(transaction);
        switch (whichFragment) {
            case MAIN_PAGE:
                if (mMainFragment == null) {
                    mMainFragment = new MainPageFragment();
                    transaction.add(R.id.frameLayout, mMainFragment);
                } else {
                    transaction.show(mMainFragment);
                }
                break;
            case COURSE:
                if (mCourseFragment == null) {
                    mCourseFragment = new CourseFragment();
                    transaction.add(R.id.frameLayout, mCourseFragment);
                } else transaction.show(mCourseFragment);
                break;
            case VIP:
                if (mVipFragment == null) {
                    mVipFragment = new VipFragment();
                    transaction.add(R.id.frameLayout, mVipFragment);
                } else transaction.show(mVipFragment);
                break;
            case DATA:
                if (mDataFragment == null) {
                    mDataFragment = new DataFragment();
                    transaction.add(R.id.frameLayout, mDataFragment);
                } else transaction.show(mDataFragment);
                break;
            case MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.frameLayout, mMineFragment);
                } else transaction.show(mMineFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }
}
