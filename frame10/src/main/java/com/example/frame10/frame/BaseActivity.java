package com.example.frame10.frame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frame10.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

public class BaseActivity extends AppCompatActivity {
    public String TAG = "威少";
    public RecyclerView recyclerView;
    public SmartRefreshLayout mRefreshLayout;
    public FrameApplication mFrameApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog(this.getClass().getName());
        mFrameApplication = (FrameApplication) getApplication();
    }

    public void showLog(@NonNull Object object) {
        Log.e(TAG, object.toString());
    }

    public void showToast(@NonNull Object object) {
        Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
    }

    public void initRecycler() {
        recyclerView = findViewById(com.example.frame10.R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);
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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    public void onListRefresh() {
    }

    public void onListLoadMore() {
    }

    public String getContent(TextView view) {
        return view != null ? view.getText().toString().trim() : "";
    }

    public int setColor(int pColor){
        return ContextCompat.getColor(this,pColor);
    }
}
