package com.example.text1.view.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.example.text1.R;
import com.example.text1.interfaces.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomTabLayout extends RelativeLayout {
    @BindView(R.id.first)
    TextView first;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.third)
    TextView third;
    @BindView(R.id.forth)
    TextView forth;
    @BindView(R.id.fifth)
    TextView fifth;
    private final int mTabCount;
    private final boolean mIsChangeContentColor;
    private final boolean mIsContainContent;
    private List<Integer> mSelectedDrawable;
    private List<Integer> mUnSelectedDrawable;
    private List<String> mContent;
    private List<TextView> mViewList = new ArrayList<>();
    @ColorInt
    private int mSelectedContentColor, mUnSelectedContentColor;
    private int mDefaultShow;

    public int getDefaultShow() {
        return mDefaultShow;
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_tab_layout, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomTabLayout, 0, 0);
        mTabCount = ta.getInt(R.styleable.BottomTabLayout_num, 5);
        mIsChangeContentColor = ta.getBoolean(R.styleable.BottomTabLayout_isChangeContentColor, true);
        mIsContainContent = ta.getBoolean(R.styleable.BottomTabLayout_isContainContent, true);
        mDefaultShow = ta.getInt(R.styleable.BottomTabLayout_default_show, 1);
        mSelectedContentColor = ta.getColor(R.styleable.BottomTabLayout_content_selected_color, Color.RED);
        mUnSelectedContentColor = ta.getColor(R.styleable.BottomTabLayout_content_unSelected_color, Color.BLACK);
        Collections.addAll(mViewList, first, second, third, forth, fifth);
        for (int i = 0; i < 5 - mTabCount; i++) {
            mViewList.get(mViewList.size() - 1).setVisibility(GONE);
            mViewList.remove(mViewList.size() - 1);
        }
        ta.recycle();
    }

    public String setResource(@NonNull List<Integer> pSelectedDrawable, @NonNull List<Integer> pUnSelectedDrawable, List<String> pContent) {
        mSelectedDrawable = pSelectedDrawable;
        mUnSelectedDrawable = pUnSelectedDrawable;
        if (mIsContainContent) mContent = pContent;
        if (mSelectedDrawable.size() < mTabCount || mUnSelectedDrawable.size() < mTabCount || mIsContainContent&&mContent.size()<mTabCount)return "资源数量少于需求数量";
        setStyle();
        return "";
    }

    private void setStyle() {
        for (int i = 0; i < mViewList.size(); i++) {
            TextView view = mViewList.get(i);
            if (mContent != null && mContent.size() != 0) {
                view.setText(mContent.get(i));
                view.setTextColor(mUnSelectedContentColor);
                if (mIsChangeContentColor && mDefaultShow == i + 1) {
                    view.setTextColor(mSelectedContentColor);
                }
            } else {
                view.setTextSize(0f);
            }
            view.setCompoundDrawablesWithIntrinsicBounds(0, mDefaultShow != i + 1 ? mUnSelectedDrawable.get(i) : mSelectedDrawable.get(i), 0, 0);
        }
    }

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener pOnRecyclerItemClickListener){
        mOnRecyclerItemClickListener = pOnRecyclerItemClickListener;
    }

    @OnClick({R.id.first, R.id.second, R.id.third, R.id.forth, R.id.fifth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first:
                mDefaultShow = 1;
                break;
            case R.id.second:
                mDefaultShow = 2;
                break;
            case R.id.third:
                mDefaultShow = 3;
                break;
            case R.id.forth:
                mDefaultShow = 4;
                break;
            case R.id.fifth:
                mDefaultShow = 5;
                break;
        }
        setStyle();
        if (mOnRecyclerItemClickListener != null) mOnRecyclerItemClickListener.onItemClick(mDefaultShow);
    }
}
