package com.liu.anew.activity.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.fragment.FourFragment;
import com.liu.anew.fragment.ThreeFragment;
import com.liu.anew.home.one.HomeFragment;
import com.liu.anew.fragment.TwoFragment;
import com.liu.anew.fragment.UserFragment;

import java.util.ArrayList;

/**
 * BottomNavigationView+FrameLayout
 */
public class MainActivity extends BaseActivity {

    private ArrayList<BaseFragment> fragments;
    private int position;
    private BaseFragment mContext;
    private BaseFragment baseFragment;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        fragments.add(new UserFragment());
        switchFragment(mContext, getFragment(0));
    }

    @Override
    public void initListener() {

    }
    /**
     *
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
        if (mContext != nextFragment) {
            mContext = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
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
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_first:
                    position = 0;
                    baseFragment = getFragment(position);
                    switchFragment(mContext, baseFragment);
                    return true;
                case R.id.navigation_second:
                    position = 1;
                     baseFragment = getFragment(position);
                    switchFragment(mContext, baseFragment);
                    return true;
                case R.id.navigation_three:
                    position = 2;
                     baseFragment = getFragment(position);
                    switchFragment(mContext, baseFragment);
                    return true;
            }
            return false;
        }
    };
}
