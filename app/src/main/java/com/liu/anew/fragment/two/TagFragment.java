package com.liu.anew.fragment.two;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.liu.anew.R;
import com.liu.anew.adapter.ConstellationAdapter;
import com.liu.anew.adapter.GirdDropDownAdapter;
import com.liu.anew.adapter.ListDropDownAdapter;
import com.liu.anew.base.BaseFragment;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private String headers[] = {"城市", "年龄", "性别", "星座"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;
    private FragmentActivity activity;
    private GridView constellation;
    private String one = "不限", two = "不限", three = "不限", four = "不限";
    private TextView contentView;
    private TextView tvContent;


    @Override
    public View initView() {
        activity = getActivity();
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        return view;
    }

    @Override
    public void initData() {
        //init city menu
        final ListView cityView = new ListView(activity);
        cityAdapter = new GirdDropDownAdapter(activity, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(activity);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(activity, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(getActivity());
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(activity, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                one = citys[position];
                tvContent.setText(one + two + three + four);
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();

                two = ages[position];
                tvContent.setText(one + two + three + four);
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();

                three = sexs[position];
                tvContent.setText(one + two + three + four);
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;

                four = constellations[position];
                tvContent.setText(one + two + three + four);
            }
        });

        //init context view
        View view = getLayoutInflater().inflate(R.layout.item_channel, null);
        tvContent = view.findViewById(R.id.tv_channel);

//        TextView contentView = new TextView(getActivity());
//        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initListener() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
