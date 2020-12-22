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

/**
 * Created by 任小龙 on 2020/10/15.
 */
public class CityAndAreaAdapter extends RecyclerView.Adapter<CityAndAreaAdapter.ViewHolder> {
    private List<AreaInfo> mList;
    private int type;
    private List<String> mSelected;
    private Context mContext;

    public CityAndAreaAdapter(List<AreaInfo> pList, int type,List<String> mSelected) {
        mList = pList;
        this.type = type;
        this.mSelected = mSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_or_area_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).name);
        holder.name.setTextColor(ContextCompat.getColor(mContext, !mSelected.contains(mList.get(position).name)?R.color.city_name_un_selected:R.color.city_green));
        holder.name.setOnClickListener(v->{
            if (mOnRecyclerItemClickListener != null){
                mOnRecyclerItemClickListener.onItemClick(position,type);
            }
        });
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
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
