package com.example.text1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.BannerDataInfo;
import com.example.datalibrary.LiveDataInfo;
import com.example.datalibrary.MainPageListInfo;
import com.example.text1.R;
import com.example.text1.view.customs.BannerLayout;
import com.example.utils.newAdd.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageListAdapter extends RecyclerView.Adapter {
    private Activity mContext;
    private List<BannerDataInfo> mBannerList;
    private List<MainPageListInfo> mListData;
    private List<LiveDataInfo> mLiveData;
    private final int BANNER = 1, LABEL = 2, SMALL_IMAGE = 3, BIG_IMAGE = 4;

    public MainPageListAdapter(Activity pContext, List<BannerDataInfo> pBannerList, List<MainPageListInfo> pListData, List<LiveDataInfo> mLiveData) {
        mContext = pContext;
        mBannerList = pBannerList;
        mListData = pListData;
        this.mLiveData = mLiveData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case BANNER:
                viewHolder = new ViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.banner_layout, parent, false));
                break;
            case LABEL:
                viewHolder = new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.lable_layout, parent, false));
                break;
            case SMALL_IMAGE:
                viewHolder = new ViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.small_image_layout, parent, false));
                break;
            case BIG_IMAGE:
                viewHolder = new ViewHolder4(LayoutInflater.from(mContext).inflate(R.layout.big_image_layout, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderPre, int position) {

        if (getItemViewType(position) == SMALL_IMAGE) {
            MainPageListInfo listInfo = mListData.get(position - 2);
            ViewHolder3 holder = (ViewHolder3) holderPre;
            holder.title.setText(listInfo.title);
            holder.scanNum.setText(listInfo.view_num + "人浏览");
            holder.followNum.setText(listInfo.reply_num + "人跟帖");
            holder.date.setText(listInfo.date);
            GlideUtil.loadImage(holder.rightImage, listInfo.pic);
        } else if (getItemViewType(position) == BIG_IMAGE) {
            MainPageListInfo listInfo = mListData.get(position - 2);
            ViewHolder4 holder = (ViewHolder4) holderPre;
            holder.bigTitle.setText(listInfo.title);
            holder.bigFirstNum.setText(listInfo.view_num + "人浏览");
            holder.bigSecondNum.setText(listInfo.price + "人民币");
            GlideUtil.loadImage(holder.bigImage, listInfo.pic);
        } else if (getItemViewType(position) == LABEL) {
            ViewHolder2 holder = (ViewHolder2) holderPre;
            if (mLiveData.size() == 0) {
                holder.liveGroup.setVisibility(View.GONE);
            } else {
                if (holder.liveGroup.getVisibility() == View.GONE)
                    holder.liveGroup.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                holder.liveRecycler.setLayoutManager(manager);
                LiveAdapter liveAdapter = new LiveAdapter(mLiveData);
                holder.liveRecycler.setAdapter(liveAdapter);
            }
        } else {
            ViewHolder1 holder = (ViewHolder1) holderPre;
            List<String> tempList = new ArrayList<>();
            for (BannerDataInfo info : mBannerList) tempList.add(info.img);
            holder.mBannerLayout.attachActivity(mContext);
            holder.mBannerLayout.setViewUrls(tempList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return BANNER;
        else if (position == 1) return LABEL;
        else if (mListData.get(position - 2).type == 3) return SMALL_IMAGE;
        else return BIG_IMAGE;
    }

    @Override
    public int getItemCount() {
        return mListData != null && mBannerList != null ? mListData.size() + 2 : 0;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.banner_main)
        BannerLayout mBannerLayout;


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.train_text)
        TextView trainText;
        @BindView(R.id.best_text)
        TextView bestText;
        @BindView(R.id.discuss_text)
        TextView discussText;
        @BindView(R.id.answer_text)
        TextView answerText;
        @BindView(R.id.gift_text)
        TextView giftText;
        @BindView(R.id.live_group)
        Group liveGroup;
        @BindView(R.id.live_recycler)
        RecyclerView liveRecycler;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.right_image)
        ImageView rightImage;
        @BindView(R.id.scan_num)
        TextView scanNum;
        @BindView(R.id.follow_num)
        TextView followNum;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.right_image_itemView)
        ConstraintLayout rightImageItemView;


        public ViewHolder3(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder4 extends RecyclerView.ViewHolder {
        @BindView(R.id.big_image)
        ImageView bigImage;
        @BindView(R.id.big_first_num)
        TextView bigFirstNum;
        @BindView(R.id.big_second_num)
        TextView bigSecondNum;
        @BindView(R.id.big_title)
        TextView bigTitle;
        @BindView(R.id.big_item)
        ConstraintLayout bigItem;

        public ViewHolder4(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
