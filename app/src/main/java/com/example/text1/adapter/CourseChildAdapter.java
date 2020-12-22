package com.example.text1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datalibrary.CourseChildListInfo;
import com.example.text1.R;
import com.example.utils.newAdd.GlideUtil;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseChildAdapter extends RecyclerView.Adapter<CourseChildAdapter.ViewHolder> {
    List<CourseChildListInfo.ListContent> mListData;

    public CourseChildAdapter(List<CourseChildListInfo.ListContent> pListData) {
        mListData = pListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_child_list_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseChildListInfo.ListContent content = mListData.get(position);
        GlideUtil.loadImage(holder.leftImage,content.thumb);
        holder.title.setText(content.lesson_name);
        holder.learnNum.setText(content.studentnum+"人学习");
        holder.goodNum.setText("好评率"+content.rate);
        holder.price.setText("￥"+content.price);
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_image)
        ImageView leftImage;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.learn_num)
        TextView learnNum;
        @BindView(R.id.good_num)
        TextView goodNum;
        @BindView(R.id.price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
