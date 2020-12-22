package com.example.text1.view.fragment;

import androidx.fragment.app.Fragment;

import com.example.frame10.frame.BaseMvpFragment;
import com.example.text1.Models.VipModel;
import com.example.text1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseMvpFragment<VipModel> {

    @Override
    public int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setUpData() {

    }

    @Override
    public VipModel setModel() {
        return null;
    }

    @Override
    public void onDataBack(int whichApi, Object... pObjects) {

    }
}
