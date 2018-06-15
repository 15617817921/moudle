package com.liu.anew.home.one;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jet.sweettips.util.SnackbarUtils;
import com.liu.anew.R;
import com.liu.anew.activity.home.MsgAct;
import com.liu.anew.adapter.HomeRecycleAdapter;
import com.liu.anew.application.MyApplication;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.bean.data.ResultBean;
import com.liu.anew.utils.JsonUtil;
import com.liu.anew.utils.MyToast;
import com.liu.anew.utils.ScreenUtil1;

import com.liu.anew.utils.ToastUtils;
import com.liuguangqiang.cookie.CookieBar;
import com.liuguangqiang.cookie.OnActionClickListener;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_saomiao)
    ImageView tvSaomiao;
    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    Unbinder unbinder;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.ib_top)
    ImageButton ibTop;
    @BindView(R.id.titlebar)
    LinearLayout titlebar;
    private HomeRecycleAdapter adapter;
    private ResultBean resultBean;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        return view;
    }

    @Override
    public void initData() {
        processData();
        adapter = new HomeRecycleAdapter(mContext, resultBean);
        rvHome.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);

        //设置滑动到哪个位置了的监听
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    ibTop.setVisibility(View.GONE);
                } else {
                    ibTop.setVisibility(View.VISIBLE);
                }
                return 1;
            }
        });
        //设置网格布局
        rvHome.setLayoutManager(manager);

        initListener();
    }

    private void processData() {
        /*获取到assets文件下的TExt.json文件的数据，并以输出流形式返回。*/
//        InputStream is = new InputStreamReader(this.getAssets().open("testjson.json"),"UTF-8");
        String json = JsonUtil.getJson(mContext, "testjson.json");
        log.e(json);

        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = JSON.parseObject(json);
            //得到状态码
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");


            //得到resultBean的数据
            JSONObject ResultBean = JSON.parseObject(result, ResultBean.class);
            String banner_info = ResultBean.getString("banner_info");
            String act_info = ResultBean.getString("act_info");
            String channel_info = ResultBean.getString("channel_info");
            String hot_info = ResultBean.getString("hot_info");
            String recommend_info = ResultBean.getString("recommend_info");
            String seckill_info = ResultBean.getString("seckill_info");


            resultBean = new ResultBean();

            //设置BannerInfoBean数据
            List<ResultBean.BannerInfoBean> bannerInfoBeans = JSON.parseArray(banner_info, ResultBean.BannerInfoBean.class);
            resultBean.setBanner_info(bannerInfoBeans);
            String value = jsonObject.getString("value");
            ResultBean.BannerInfoBean.ValueBean valueBean = JSON.parseObject(value, ResultBean.BannerInfoBean.ValueBean.class);


            //设置actInfoBeans数据
            List<ResultBean.ActInfoBean> actInfoBeans = JSON.parseArray(act_info, ResultBean.ActInfoBean.class);
            resultBean.setAct_info(actInfoBeans);

            //设置channelInfoBeans的数据
            List<ResultBean.ChannelInfoBean> channelInfoBeans = JSON.parseArray(channel_info, ResultBean.ChannelInfoBean.class);
            resultBean.setChannel_info(channelInfoBeans);

            //设置hotInfoBeans的数据
            List<ResultBean.HotInfoBean> hotInfoBeans = JSON.parseArray(hot_info, ResultBean.HotInfoBean.class);
            resultBean.setHot_info(hotInfoBeans);

            //设置recommendInfoBeans的数据
            List<ResultBean.RecommendInfoBean> recommendInfoBeans = JSON.parseArray(recommend_info, ResultBean.RecommendInfoBean.class);
            resultBean.setRecommend_info(recommendInfoBeans);

            //设置seckillInfoBean的数据
            ResultBean.SeckillInfoBean seckillInfoBean = JSON.parseObject(seckill_info, ResultBean.SeckillInfoBean.class);
            resultBean.setSeckill_info(seckillInfoBean);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private int[][] animationValues = new int[][]{
            {R.anim.toast_enter, R.anim.toast_exit},
            {R.anim.scale_enter, R.anim.scale_exit},
            {R.anim.slide_in_left, R.anim.slide_out_left},
            {R.anim.scale_enter2, R.anim.scale_exit2}
    };
    private int[][] snackbarAnims = new int[][]{
            {R.anim.scale_enter, R.anim.scale_exit},
            {R.anim.slide_in_left, R.anim.slide_out_left},
            {R.anim.toast_enter_miui, R.anim.toast_exit}
    };
    private int snackbarAnimIndex = 0;

    @OnClick({R.id.tv_saomiao, R.id.tv_search_home, R.id.tv_message_home, R.id.ib_top})
    public void onViewClicked(View view) {
        int height = ScreenUtil1.getScreenHeight(getActivity());
        int statusHeight = ScreenUtil1.getStatusHeight(getActivity());
        int actionBarHeight = ScreenUtil1.getActionBarHeight(getActivity());
        log.e(height + "--" + statusHeight + "--" + actionBarHeight);
        switch (view.getId()) {

            case R.id.tv_saomiao:
                SnackbarUtils.Long(tvSaomiao, "从顶部滑入界面")
                        .gravityFrameLayout(Gravity.TOP)
                        .margins(0, statusHeight, 0, 0)
                        .anim(R.anim.snackbar_down_in, R.anim.snackbar_up_out)
                        .backColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                        .show();
                MyToast.Builder builder = new MyToast.Builder(getActivity()).setGravity(MyToast.GRAVITY_BOTTOM).setFirstText("底部").setSecondText("提醒");
                MyApplication.getInstance().toastShowByBuilder(builder);
                break;
            case R.id.tv_search_home:
                SnackbarUtils.Long(tvSearchHome, "Snackbar自定义动画").anim(snackbarAnims[snackbarAnimIndex][0], snackbarAnims[snackbarAnimIndex][1]).backColor(Color.BLUE).show();
                snackbarAnimIndex = (++snackbarAnimIndex) % (snackbarAnims.length);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.Builder builder = new MyToast.Builder(getActivity()).setGravity(MyToast.GRAVITY_TOP).setFirstText("底部").setSecondText("提醒");
                        MyApplication.getInstance().toastShowByBuilder(builder);
                    }
                }).start();
                break;
            case R.id.tv_message_home:
                Intent intent = new Intent(mContext, MsgAct.class);
                startActivity(intent);
                break;
            case R.id.ib_top:
                rvHome.scrollToPosition(0);
                MyToast.Builder builder1 = new MyToast.Builder(getActivity()).setGravity(MyToast.GRAVITY_CENTER).setFirstText("底部").setSecondText("提醒");
                MyApplication.getInstance().toastShowByBuilder(builder1);
                break;
        }


    }


}
