package com.liu.anew.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.bean.custom.TabEntity;
import com.liu.anew.fragment.ThreeFragment;
import com.liu.anew.home.one.HomeFragment;
import com.liu.anew.fragment.FourFragment;
import com.liu.anew.fragment.TwoFragment;
import com.liu.anew.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class Main4Activity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablout)
    CommonTabLayout tablout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mTitles = {"首页", "消息", "联系人", "更多", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.false01, R.drawable.false02,
            R.drawable.false03, R.drawable.false04, R.drawable.false05};
    private int[] mIconSelectIds = {
            R.drawable.ture01, R.drawable.ture02,
            R.drawable.ture03, R.drawable.ture04, R.drawable.ture05};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main4;
    }

    @Override
    public void initView() {
        fragments.add(new HomeFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        fragments.add(new UserFragment());


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
//        mTabLayout_3 = (CommonTabLayout) findViewById(R.id.tl_3);
//        mTabLayout_3.setTabData(mTabEntities, this, R.id.fl_change, fragments);
        tablout = (CommonTabLayout) findViewById(R.id.tablout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void initData() {
        tablout.setTabData(mTabEntities);
        tablout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    tablout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(0);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
