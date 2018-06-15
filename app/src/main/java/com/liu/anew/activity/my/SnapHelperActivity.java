package com.liu.anew.activity.my;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.liu.anew.R;
import com.liu.anew.base.BaseRecyclerAdapter;
import com.liu.anew.base.SmartViewHolder;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Arrays;
import java.util.Collection;

import butterknife.BindView;

public class SnapHelperActivity extends BaseActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BaseRecyclerAdapter<Integer> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_snap_helper;
    }

    @Override
    public void initView() {
   recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter=new BaseRecyclerAdapter<Integer>(loadModels(), R.layout.listitem_example_snaphelper) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Integer model, int position) {
                holder.image(R.id.imageView, model);
                final TextView tv= (TextView) holder.findViewById(R.id.tv);
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showLong(tv.getText());
                    }
                });
            }
        });
//        LinearSnapHelper 可以快速滑动   PagerSnapHelper  一次只滑动一页
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showLong("position---"+position);
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottomSheetDialog dialog=new BottomSheetDialog(mContext);
                View dialogView = View.inflate(mContext, R.layout.listitem_example_snaphelper, null);
                dialog.setContentView(dialogView);
//                AbsListView listView = (AbsListView) dialogView.findViewById(R.id.listView);
//                listView.setAdapter(mAdapter);
//                ViewGroup parentGroup = (ViewGroup) listView.getParent();
//                parentGroup.removeView(listView);
////                ViewGroup parentRoot = (ViewGroup) parentGroup.getParent();
////                parentRoot.removeView(parentGroup);
//                RecyclerView recyclerView = new RecyclerView(mContext);
//                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//                recyclerView.setAdapter(mAdapter);
//                ((SmartRefreshLayout) parentGroup).setEnableRefresh(false);
////                ((SmartRefreshLayout) parentGroup).startNestedScroll(0);
////                ((SmartRefreshLayout) parentGroup).setEnableOverScrollDrag(false);
//                ((SmartRefreshLayout) parentGroup).setRefreshContent(recyclerView);
//                parentRoot.addView(recyclerView, -1, -1);
                dialog.show();
            }
        });
    }

    private Collection<Integer> loadModels() {
        return Arrays.asList(
                R.mipmap.image_weibo_home_1,
                R.mipmap.image_weibo_home_2,
                R.mipmap.image_weibo_home_1,
                R.mipmap.image_weibo_home_2,
                R.mipmap.image_weibo_home_1,
                R.mipmap.image_weibo_home_2);
    }

}
