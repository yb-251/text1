package com.example.text1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.SubjectInfo;
import com.example.text1.R;
import com.example.text1.interfaces.OnRecyclerItemClickListener;

import java.util.List;

public class ChooseSpecialInnerAdapter extends RecyclerView.Adapter<ChooseSpecialInnerAdapter.ViewHolder> {
    private Context mContext;
    private List<SubjectInfo.SubjectDataInfo> mData;
    private String subId;

    public ChooseSpecialInnerAdapter(Context pContext, List<SubjectInfo.SubjectDataInfo> pData, String subId) {
        mContext = pContext;
        mData = pData;
        this.subId = subId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_inner_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.content.setText(mData.get(position).specialty_name);
        if (!subId.equals(mData.get(position).specialty_id)) {
            holder.contentBg.setBackgroundResource(R.drawable.subject_normal_bg);
            holder.content.setTextColor(Color.BLACK);
        } else {
            holder.contentBg.setBackgroundResource(R.drawable.subject_selected_bg);
            holder.content.setTextColor(Color.WHITE);
        }
        holder.contentBg.setOnClickListener(v->{
            if (mOnRecyclerItemClickListener != null)mOnRecyclerItemClickListener.onItemClick(position);
        });
    }

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener on){
        mOnRecyclerItemClickListener = on;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout contentBg;
        TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            contentBg = itemView.findViewById(R.id.item_bg);
        }
    }
}
