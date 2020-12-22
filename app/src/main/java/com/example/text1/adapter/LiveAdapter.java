package com.example.text1.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.LiveDataInfo;
import com.example.datalibrary.VipBannerAndLiveInfo;
import com.example.text1.R;
import com.example.text1.view.customs.RoundOrCircleImage;
import com.example.utils.newAdd.GlideUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveAdapter extends RecyclerView.Adapter {
    private List<LiveDataInfo> mLiveData;
    private List<VipBannerAndLiveInfo.LiveVipListInfo> pLive;
    private final int FIRST = 100, OTHER = 101;
    private int fromVip;


    public LiveAdapter(List<LiveDataInfo> pLiveData) {
        mLiveData = pLiveData;
    }

    public LiveAdapter(List<VipBannerAndLiveInfo.LiveVipListInfo> pLive,int from) {
        this.pLive = pLive;
        this.fromVip = from;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FIRST){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_first_adapter_layout, parent, false));
        } else {
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_other_adapter_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderPre, int position) {
        if (getItemViewType(position) == FIRST){
            ViewHolder holder = (ViewHolder) holderPre;
            if (fromVip == 0){
                GlideUtil.loadImage(holder.livePhoto,mLiveData.get(position).getTeacher_photo());
                holder.liveTitle.setText(mLiveData.get(position).getLive_name());
                if (!TextUtils.isEmpty(mLiveData.get(position).getStartDateTime())){
                    String time = mLiveData.get(position).getStartDateTime()+".";
                    String substring = time.substring(time.length() - 6, time.length() - 1);
                    holder.liveTime.setText("今天"+substring);
                }
            } else {
                GlideUtil.loadImage(holder.livePhoto,pLive.get(position).teacher_photo);
                holder.liveTitle.setText(pLive.get(position).live_name);
                if (!TextUtils.isEmpty(pLive.get(position).startDateTime)){
                    String time = pLive.get(position).startDateTime+".";
                    String substring = time.substring(time.length() - 6, time.length() - 1);
                    holder.liveTime.setText("今天"+substring);
                }
            }

        } else {
            ViewHolder2 holder = (ViewHolder2)holderPre;
            if (fromVip == 0){
                GlideUtil.loadImage(holder.livePhoto,mLiveData.get(position).getTeacher_photo());
                holder.liveTitle.setText(mLiveData.get(position).getLive_name());
                if (!TextUtils.isEmpty(mLiveData.get(position).getStartDateTime())){
                    String time = mLiveData.get(position).getStartDateTime()+".";
                    String substring = time.substring(time.length() - 6, time.length() - 1);
                    holder.liveTime.setText("今天"+substring);
                }
            } else {
                GlideUtil.loadImage(holder.livePhoto,pLive.get(position).teacher_photo);
                holder.liveTitle.setText(pLive.get(position).live_name);
                if (!TextUtils.isEmpty(pLive.get(position).startDateTime)){
                    String time = pLive.get(position).startDateTime+".";
                    String substring = time.substring(time.length() - 6, time.length() - 1);
                    holder.liveTime.setText("今天"+substring);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FIRST;
        } else return OTHER;
    }

    @Override
    public int getItemCount() {
        List<? extends Serializable> list = fromVip != 0 ?  pLive : mLiveData;
        return list != null ? list.size() > 3 ? 3 : list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.live_photo)
        RoundOrCircleImage livePhoto;
        @BindView(R.id.live_title)
        TextView liveTitle;
        @BindView(R.id.live_time)
        TextView liveTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.live_photo)
        RoundOrCircleImage livePhoto;
        @BindView(R.id.live_title)
        TextView liveTitle;
        @BindView(R.id.live_time)
        TextView liveTime;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
