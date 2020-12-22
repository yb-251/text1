package com.example.text1.view.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.frame10.frame.BaseMvpFragment;
import com.example.frame10.frame.ICommonModel;
import com.example.text1.Models.DataModel;
import com.example.text1.R;
import com.example.text1.adapter.MyFragmentAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends BaseMvpFragment {

    @BindView(R.id.tab_layout)
    SegmentTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();
    private String[] str = {"资料小组", "最新精华"};
    private MyFragmentAdapter mFragmentAdapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_data;
    }

    @Override
    public void setUpView() {
        mFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, mTitle);
        viewPager.setAdapter(mFragmentAdapter);
        tabLayout.setTabData(str);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setUpData() {
        Collections.addAll(mTitle, "资料小组", "最新精华");
        Collections.addAll(mFragments, DataGroupFragment.getInstance(), NewBestFragment.getInstance());
        mFragmentAdapter.notifyDataSetChanged();
        tabLayout.notifyDataSetChanged();
    }

    @Override
    public DataModel setModel() {
        return null;
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }
}
