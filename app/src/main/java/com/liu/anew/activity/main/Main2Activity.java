package com.liu.anew.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.fragment.ThreeFragment;
import com.liu.anew.home.one.HomeFragment;
import com.liu.anew.fragment.FourFragment;
import com.liu.anew.fragment.TwoFragment;
import com.liu.anew.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends BaseActivity {


    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_type)
    RadioButton rbType;
    @BindView(R.id.rb_community)
    RadioButton rbCommunity;
    @BindView(R.id.rb_cart)
    RadioButton rbCart;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.fl_framelayout)
    FrameLayout flFramelayout;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private BaseFragment baseFragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {

        fragments = new ArrayList<>();

        fragments.add(new HomeFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        fragments.add(new UserFragment());

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        log.e("000");
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
//                        dismissAnmiation();
//                        typeFragment.hideFragment();
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
//                        typeFragment.hideFragment();

                        break;
                    case R.id.rb_cart:
                        position = 3;
//                        fragments.remove(fragments.get(3));
//                        FourFragment cartFragment = new FourFragment();
//                        fragments.add(3, cartFragment);
//
//                        typeFragment.hideFragment();
                        break;
                    case R.id.rb_user:

                            position = 4;

                        break;
                }

                BaseFragment baseFragment = getFragment(position);
                switchFragment(baseFragment, baseFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }


    /**
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (baseFragment != nextFragment) {
            baseFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.fl_framelayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}

