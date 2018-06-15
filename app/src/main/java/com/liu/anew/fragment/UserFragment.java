package com.liu.anew.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.anew.R;
import com.liu.anew.activity.my.NineActivity;
import com.liu.anew.activity.my.PhotoActivity;
import com.liu.anew.activity.my.ProfilePracticeActivity;
import com.liu.anew.activity.my.SnapHelperActivity;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.utils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;
    private ImageButton ibUserIconAvator;
    private TextView tvUsername;
    private TextView tvAllOrder;
    private TextView tvUserPay;
    private TextView tvUserReceive;
    private TextView tvUserFinish;
    private TextView tvUserDrawback;
    private TextView tvUserLocation;
    private TextView tvUserCollect;
    private TextView tvUserCoupon;
    private TextView tvUserScore;
    private TextView tvUserPrize;
    private TextView tvUserTicket;
    private TextView tvUserInvitation;
    private TextView tvUserCallcenter;
    private TextView tvUserFeedback;
    private TextView tvUsercenter;
    private ImageButton ibUserSetting;
    private ImageButton ibUserMessage;
    private ScrollView scrollView;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-10-08 09:07:21 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     *
     * @param view
     */
    private void findViews(View view) {
        ibUserIconAvator = (ImageButton) view.findViewById(R.id.ib_user_icon_avator);
        tvUsername = (TextView) view.findViewById(R.id.tv_username);
        tvAllOrder = (TextView) view.findViewById(R.id.tv_all_order);
        tvUserPay = (TextView) view.findViewById(R.id.tv_user_pay);
        tvUserReceive = (TextView) view.findViewById(R.id.tv_user_receive);
        tvUserFinish = (TextView) view.findViewById(R.id.tv_user_finish);
        tvUserDrawback = (TextView) view.findViewById(R.id.tv_user_drawback);
        tvUserLocation = (TextView) view.findViewById(R.id.tv_user_location);
        tvUserCollect = (TextView) view.findViewById(R.id.tv_user_collect);
        tvUserCoupon = (TextView) view.findViewById(R.id.tv_user_coupon);
        tvUserScore = (TextView) view.findViewById(R.id.tv_user_score);
        tvUserPrize = (TextView) view.findViewById(R.id.tv_user_prize);
        tvUserTicket = (TextView) view.findViewById(R.id.tv_user_ticket);
        tvUserInvitation = (TextView) view.findViewById(R.id.tv_user_invitation);
        tvUserCallcenter = (TextView) view.findViewById(R.id.tv_user_callcenter);
        tvUserFeedback = (TextView) view.findViewById(R.id.tv_user_feedback);
        tvUsercenter = (TextView) view.findViewById(R.id.tv_usercenter);
        ibUserSetting = (ImageButton) view.findViewById(R.id.ib_user_setting);
        ibUserMessage = (ImageButton) view.findViewById(R.id.ib_user_message);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);

        ibUserIconAvator.setOnClickListener(this);
        ibUserSetting.setOnClickListener(this);
        ibUserMessage.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-10-08 09:07:21 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == ibUserIconAvator) {
//            Intent intent = new Intent(mContext, LoginActivity.class);
////            startActivityForResult(intent, 0);
//            startActivity(intent);
            Toast.makeText(mContext, "头像", Toast.LENGTH_SHORT).show();
        } else if (v == ibUserSetting) {
            Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
        } else if (v == ibUserMessage) {
            Toast.makeText(mContext, "消息", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(mContext, MessageCenterActivity.class);
//            startActivity(intent);
        }
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        findViews(view);
        tvUsercenter.setAlpha(0);
        return view;
    }

    @Override
    public void initData() {

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            String screen_name = data.getStringExtra("screen_name");
            String profile_image_url = data.getStringExtra("profile_image_url");

            Picasso.get().load(profile_image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须队更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ibUserIconAvator);

            tvUsername.setText(screen_name);
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

    @OnClick({R.id.tv_user_location, R.id.tv_user_collect, R.id.tv_user_coupon, R.id.tv_user_score, R.id.tv_user_prize, R.id.tv_user_ticket, R.id.tv_user_invitation, R.id.tv_user_callcenter, R.id.tv_user_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_location:
                gotoAtivity(ProfilePracticeActivity.class,null);
                break;
            case R.id.tv_user_collect:
                gotoAtivity(SnapHelperActivity.class,null);
                break;
            case R.id.tv_user_coupon:
                gotoAtivity(PhotoActivity.class,null);
                break;
                //仿微信朋友圈图片展示
            case R.id.tv_user_score:
                gotoAtivity(NineActivity.class,null);
                break;
            case R.id.tv_user_prize:
                break;
            case R.id.tv_user_ticket:
                break;
            case R.id.tv_user_invitation:
                break;
            case R.id.tv_user_callcenter:
                break;
            case R.id.tv_user_feedback:
                break;
        }
    }
}
