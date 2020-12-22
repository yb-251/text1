package com.example.text1.view.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.CourseModel;
import com.example.text1.R;
import com.example.text1.adapter.MyFragmentAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseMvpFragment<CourseModel> {
    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private MyFragmentAdapter mMyFragmentAdapter;
    private final int TRAIN = 3,BEST = 1,PUBLIC = 2;

    @Override
    public int setLayout() {
        return R.layout.fragment_course;
    }

    @Override
    public void setUpView() {
        mMyFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        viewPager.setAdapter(mMyFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    @Override
    public void setUpData() {
        Collections.addAll(mTitleList,"训练营","精品课","公开课");
        Collections.addAll(mFragmentList,CourseChildFragment.getInstance(TRAIN),CourseChildFragment.getInstance(BEST),CourseChildFragment.getInstance(PUBLIC));
        mMyFragmentAdapter.notifyDataSetChanged();
        slideLayout.notifyDataSetChanged();
    }

    @Override
    public CourseModel setModel() {
        return null;
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }
}
