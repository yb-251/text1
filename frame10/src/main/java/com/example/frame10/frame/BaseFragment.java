package com.example.frame10.frame;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frame10.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class BaseFragment extends Fragment {

    public String TAG = "威少";
    public RecyclerView recyclerView;
    public SmartRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLog(this.getClass().getName());
    }

    public void showLog(@NonNull Object object) {
        Log.e(TAG, object.toString());
    }

    public void showToast(@NonNull Object object) {
        Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_SHORT).show();
    }

    public void initRecycler() {
        recyclerView = getView().findViewById(com.example.frame10.R.id.recyclerView);
        mRefreshLayout = getView().findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                onListLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onListRefresh();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
    }

    public void onListRefresh() {
    }

    public void onListLoadMore() {
    }
}
