package com.example.text1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datalibrary.AreaInfo;
import com.example.text1.R;
import com.example.text1.interfaces.OnRecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 任小龙 on 2020/10/15.
 */
public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {

    private List<AreaInfo> mList;
    private List<String> mSelected;
    private Context mContext;

    public ProvinceAdapter(List<AreaInfo> pList, Context pContext,List<String> mSelected) {
        mList = pList;
        mContext = pContext;
        this.mSelected = mSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.provice_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).name);
        holder.nameHead.setText(mList.get(position).nameHead);
        holder.name.setTextColor(ContextCompat.getColor(mContext, !mSelected.contains(mList.get(position).name)?R.color.city_name_un_selected:R.color.city_green));
        holder.nameHead.setVisibility(position != 0 && mList.get(position).nameHead.equals(mList.get(position - 1).nameHead) ? View.GONE : View.VISIBLE);
        holder.name.setOnClickListener(v -> {
            if (mOnRecyclerItemClickListener != null)
                mOnRecyclerItemClickListener.onItemClick(position);
        } );
    }

    private OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener pOnRecyclerItemClickListener) {
        mOnRecyclerItemClickListener = pOnRecyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_head)
        TextView nameHead;
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
