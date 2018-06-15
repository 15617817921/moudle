package com.liu.anew.activity.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.bean.custom.TabEntity;
import com.liu.anew.bean.data.GoodMsg;
import com.liu.anew.fragment.ThreeFragment;
import com.liu.anew.home.one.HomeFragment;
import com.liu.anew.fragment.FourFragment;
import com.liu.anew.fragment.TwoFragment;
import com.liu.anew.fragment.UserFragment;
import com.liu.anew.utils.BarUtils;
import com.liu.anew.utils.DensityUtil;
import com.liu.anew.utils.ToastUtils;
import com.liu.anew.view.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * FlycoTabLayout_Lib  CommonTabLayout+fragment 不可滑动
 */
public class Main3Activity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    CommonTabLayout mTabLayout_1;
    CommonTabLayout mTabLayout_3;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] mTitles = {"首页", "消息", "联系人", "更多", "我的"};
    private int[] mIconUnselectIds = {
            R.drawable.false01, R.drawable.false02,
            R.drawable.false03, R.drawable.false04, R.drawable.false05};
    private int[] mIconSelectIds = {
            R.drawable.ture01, R.drawable.ture02,
            R.drawable.ture03, R.drawable.ture04, R.drawable.ture05};
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main3;
    }

    private int position;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        position = savedInstanceState.getInt("position");
        log.e(position + "onRestoreInstanceState");
        mTabLayout_1.setCurrentTab(position);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        outState.putInt("position", position);
        log.e(position + "onSaveInstanceState");
    }

    @Override
    public void initView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        BarUtils.setColorForDrawerLayout(this, drawer, ContextCompat.getColor(mContext, R.color.colorPrimary), 0);
//        BarUtils.setColor(this, ContextCompat.getColor(mContext,R.color.theme_blue_dark),0);

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
        mTabLayout_1 = (CommonTabLayout) findViewById(R.id.tl_1);

        mTabLayout_1.setTabData(mTabEntities, this, R.id.fl_change, fragments);

        //当前选中
        mTabLayout_1.setCurrentTab(0);

        //1 显示未读消息
        mTabLayout_1.showMsg(0, 55);
        //2 三位数
        mTabLayout_1.showMsg(1, 100);//显示99
        mTabLayout_1.setMsgMargin(1, -5, 5);//显示99+
        //3 显示未读红点
        mTabLayout_1.showDot(2);
        MsgView rtv_2_2 = mTabLayout_1.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, DensityUtil.dip2px(this, 10f));//红点大小
            mTabLayout_1.setMsgMargin(2, -2, 1);//调整红点位置
        }
        //4 改变显示背景
        mTabLayout_1.showDot(3);
        MsgView rtv_2_3 = mTabLayout_1.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_net) {
            ToastUtils.showLong("1");
        } else if (id == R.id.menu_down) {
            ToastUtils.showLong("2");
        } else if (id == R.id.menu_image_loader) {
            ToastUtils.showLong("3");
        } else if (id == R.id.menu_other) {
            ToastUtils.showLong("4");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initData() {

        OkGo.<GoodMsg>post("http://123.57.162.168:8081/mall/app/goods/evaluation/list.json").execute(new Callback<GoodMsg>() {
            @Override
            public void onStart(Request<GoodMsg, ? extends Request> request) {
                LoadingDialog.showLoading(mContext,"加载中...");
                log.e("onStart");
            }

            @Override
            public void onSuccess(Response<GoodMsg> response) {
                log.e("onSuccess--");
            }

            @Override
            public void onCacheSuccess(Response<GoodMsg> response) {
                log.e("onCacheSuccess");
            }

            @Override
            public void onError(Response<GoodMsg> response) {
                log.e("onError"+response.getException()+"--"+response.message());
            }

            @Override
            public void onFinish() {
                LoadingDialog.hideLoading(mContext);
                log.e("onFinish");
            }

            @Override
            public void uploadProgress(Progress progress) {
                log.e("uploadProgress");
            }

            @Override
            public void downloadProgress(Progress progress) {
                log.e("downloadProgress");
            }

            @Override
            public GoodMsg convertResponse(okhttp3.Response response) throws Throwable {
                return null;
            }
        });
    }
}
