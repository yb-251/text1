package com.example.frame10.demo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.DemoInfo;
import com.example.frame10.R;
import com.example.frame10.design.RoundImage;
import com.example.utils.newAdd.GlideUtil;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private List<DemoInfo.DemoInnerDataInfo> mListData;
    private Context mContext;
    private List<String> idList;

    public DemoAdapter(List<DemoInfo.DemoInnerDataInfo> pListData, Context pContext, List<String> idList) {
        mListData = pListData;
        mContext = pContext;
        this.idList = idList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtil.loadImage(holder.roundImage, mListData.get(position).thumbnail);
        holder.titleContent.setText(mListData.get(position).title);
        holder.secondTitleContent.setText(mListData.get(position).author);
        if (idList.contains(mListData.get(position).id)) {
            holder.focusPressText.setText("取消");
            holder.focusPressText.setTextColor(Color.WHITE);
            holder.focusPressText.setBackgroundResource(R.drawable.focus_shape_selected);
        } else {
            holder.focusPressText.setText("关注");
            holder.focusPressText.setTextColor(Color.BLACK);
            holder.focusPressText.setBackgroundResource(R.drawable.focus_shape_default);
        }
        holder.focusPressText.setOnClickListener(pView -> {
            if (mOnRecyclerViewItemClick != null)mOnRecyclerViewItemClick.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundImage roundImage;
        TextView titleContent;
        TextView secondTitleContent;
        TextView focusPressText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundImage = itemView.findViewById(R.id.round_image);
            titleContent = itemView.findViewById(R.id.title_content);
            secondTitleContent = itemView.findViewById(R.id.second_title_content);
            focusPressText = itemView.findViewById(R.id.focus_press_text);
        }
    }

    public interface OnRecyclerViewItemClick{
        void onClick(int pos);
    }

    private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick pOnRecyclerViewItemClick){
        mOnRecyclerViewItemClick = pOnRecyclerViewItemClick;
    }
}
