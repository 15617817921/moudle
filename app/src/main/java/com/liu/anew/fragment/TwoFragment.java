package com.liu.anew.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.liu.anew.R;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.fragment.two.BlankFragment;
import com.liu.anew.fragment.two.ListFragment;
import com.liu.anew.fragment.two.TagFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends BaseFragment {


    SegmentTabLayout tl1;
    @BindView(R.id.iv_type_search)
    ImageView ivTypeSearch;
    @BindView(R.id.fl_type)
    FrameLayout flType;
    Unbinder unbinder;
    private List<BaseFragment> fragmentList;
    private Fragment tempFragment;
    public ListFragment listFragment;
    public TagFragment tagFragment;
    public BlankFragment blankFragment;
    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        tl1 = (SegmentTabLayout) view.findViewById(R.id.tl_1);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void initData() {
        initFragment();

        String[] titles = {"分类", "标签", "知乎"};

        tl1.setTabData(titles);

        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(tempFragment, fragmentList.get(position));
            }
            @Override
            public void onTabReselect(int position) {

            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();
        switchFragment(tempFragment, fragmentList.get(0));
    }

    public void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }

                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
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

    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = new ListFragment();
        tagFragment = new TagFragment();
        blankFragment = new BlankFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);
        fragmentList.add(blankFragment);

        switchFragment(tempFragment, fragmentList.get(0));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
