package com.example.text1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datalibrary.VipListInfo;
import com.example.text1.R;
import com.example.text1.view.customs.RoundOrCircleImage;
import com.example.utils.newAdd.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VipBottomRecyclerAdapter extends RecyclerView.Adapter<VipBottomRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<VipListInfo.VipInnerList> list;

    public VipBottomRecyclerAdapter(Context pContext, List<VipListInfo.VipInnerList> pList) {
        mContext = pContext;
        list = pList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.vip_bottom_recycle_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtil.loadImage(holder.image,list.get(position).thumb);
        holder.title.setText(list.get(position).lesson_name);
        holder.learnNum.setText(list.get(position).studentnum+"人学习");
        holder.goodNum.setText(list.get(position).comment_rate+"好评");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RoundOrCircleImage image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.learn_num)
        TextView learnNum;
        @BindView(R.id.good_num)
        TextView goodNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
