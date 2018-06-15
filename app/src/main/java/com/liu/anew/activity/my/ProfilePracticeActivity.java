package com.liu.anew.activity.my;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.utils.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfilePracticeActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_profile_practice;
    }

    @Override
    public void initView() {
        BarUtils.setColorForSwipeBack(this, ContextCompat.getColor(mContext, R.color.colorAccent), 0);
    }
}
