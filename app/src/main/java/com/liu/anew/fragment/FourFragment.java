package com.liu.anew.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liu.anew.R;
import com.liu.anew.adapter.ExpandableItemAdapter;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.bean.custom.Level0Item;
import com.liu.anew.bean.custom.Level1Item;
import com.liu.anew.bean.custom.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends BaseFragment {

    @BindView(R.id.rv_zhedie)
    RecyclerView rvZhedie;
    Unbinder unbinder;
    private ExpandableItemAdapter adapter;
    private ArrayList<MultiItemEntity> list;

    public static FourFragment getInstance() {
        FourFragment sf = new FourFragment();

        return sf;
    }

    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initData() {
        list = generateData();
        adapter = new ExpandableItemAdapter(list);

        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();
            }
        });

        rvZhedie.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        rvZhedie.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter.expandAll();
    }




    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 5;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        res.add(new Level0Item(lv0Count+"零级个数" , lv0Count+"零级"));
        return res;
    }
}
