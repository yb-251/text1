package com.example.text1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.SubjectInfo;
import com.example.frame10.design.RoundImage;
import com.example.text1.R;
import com.example.text1.interfaces.OnRecyclerItemClickListener;
import com.example.utils.newAdd.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseSpecialOutAdapter extends RecyclerView.Adapter<ChooseSpecialOutAdapter.ViewHolder> {
    private Context mContext;
    private List<SubjectInfo> mListData;
    private String subId;

    public ChooseSpecialOutAdapter(Context pContext, List<SubjectInfo> pListData,String subId) {
        mContext = pContext;
        mListData = pListData;
        this.subId = subId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.subject_out_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtil.loadImage(holder.leftIcon,mListData.get(position).icon);
        holder.subjectContent.setText(mListData.get(position).bigspecialty);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        holder.childRecycler.setLayoutManager(manager);
        List<SubjectInfo.SubjectDataInfo> data = mListData.get(position).data;
        ChooseSpecialInnerAdapter adapter = new ChooseSpecialInnerAdapter(mContext, data,subId);
        holder.childRecycler.setAdapter(adapter);
        adapter.setOnRecyclerItemClickListener(childPos->{
            subId = mListData.get(position).data.get((int)childPos[0]).specialty_id;
            notifyDataSetChanged();
            if (mOnRecyclerItemClickListener != null)mOnRecyclerItemClickListener.onItemClick(position,childPos[0]);
        });
    }

    public OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener pOnRecyclerItemClickListener){
        mOnRecyclerItemClickListener = pOnRecyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mListData != null ? mListData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_icon)
        RoundImage leftIcon;
        @BindView(R.id.subject_content)
        TextView subjectContent;
        @BindView(R.id.child_recycler)
        RecyclerView childRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
