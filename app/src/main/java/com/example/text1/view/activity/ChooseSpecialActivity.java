package com.example.text1.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datalibrary.BaseInfo;
import com.example.datalibrary.SubjectInfo;
import com.example.frame10.constants.ApiConfig;
import com.example.frame10.frame.BaseMvpActivity;
import com.example.text1.Models.LoginModel;
import com.example.text1.R;
import com.example.text1.SPConstant.Constant;
import com.example.text1.SPConstant.SPConstant;
import com.example.text1.adapter.ChooseSpecialOutAdapter;
import com.example.text1.interfaces.OnRecyclerItemClickListener;
import com.example.utils.newAdd.SharedPrefrenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSpecialActivity extends BaseMvpActivity<LoginModel> implements OnRecyclerItemClickListener {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<SubjectInfo> mListData = new ArrayList<>();
    private ChooseSpecialOutAdapter mAdapter;
    private int outPos, innerPos;
    private String mComeFrom;

    @Override
    public int setLayout() {
        return R.layout.activity_choose_special;
    }

    @Override
    public void setUpView() {
        titleContent.setText("请选择感兴趣的专业");
        rightText.setText(getString(R.string.complete));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mAdapter = new ChooseSpecialOutAdapter(this, mListData, mFrameApplication.subjectId);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public void setUpData() {
        mComeFrom = getIntent().getStringExtra(Constant.COME_FROM);
        List<SubjectInfo> tempList = mFrameApplication.subjectList;
        if (tempList != null && tempList.size() != 0) notifyMyList(tempList);
        else mPresenter.getData(ApiConfig.SUBJECT);
    }

    @Override
    public LoginModel setModel() {
        return new LoginModel();
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SubjectInfo>> baseInfo = (BaseInfo<List<SubjectInfo>>) pObjects[0];
                if (baseInfo.isSuccess()) {
                    List<SubjectInfo> result = baseInfo.result;
                    notifyMyList(result);
                    SharedPrefrenceUtils.putSerializableList(this, SPConstant.SUBJECT_LIST, result);
                }
                break;
        }
    }

    private void notifyMyList(List<SubjectInfo> pResult) {
        mListData.addAll(pResult);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.left_back, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                if (mComeFrom.equals("home")) setResult(Constant.CHOOSE_BACK_HOME);
                finish();
                break;
            case R.id.right_text:
                String specialty_id = mListData.get(outPos).data.get(innerPos).specialty_id;
                mFrameApplication.subjectId = specialty_id;
                SharedPrefrenceUtils.saveString(this, SPConstant.SUBJECT_ID, specialty_id);
                if (!mComeFrom.equals("home"))
                    startActivity(new Intent(this, HomeActivity.class));
                else setResult(Constant.CHOOSE_BACK_HOME);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mComeFrom.equals("home")) setResult(Constant.CHOOSE_BACK_HOME);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(Object[] pos) {
        outPos = (int) pos[0];
        innerPos = (int) pos[1];
    }
}
