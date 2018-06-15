package com.liu.anew.fragment.two;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.liu.anew.R;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.bean.custom.MySection;
import com.liu.anew.bean.custom.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {


    @BindView(R.id.rv_double)
    RecyclerView rvDouble;
    Unbinder unbinder;
    private List<MySection> list;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_blank, null);
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

        list = new ArrayList<>();
        list.add(new MySection(true, "Section 1", true));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(true, "Section 2", false));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(true, "Section 3", false));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(true, "Section 4", false));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(true, "Section 5", false));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));
        list.add(new MySection(new Video("", "1")));

        rvDouble.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, list);

        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MySection mySection = list.get(position);
                if (mySection.isHeader)//是头布局
                    Toast.makeText(getActivity(), mySection.header+"true", Toast.LENGTH_LONG).show();
                else//子布局
                    Toast.makeText(getActivity(), mySection.t.getName()+"false", Toast.LENGTH_LONG).show();
            }
        });
        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "onItemChildClick" + position, Toast.LENGTH_LONG).show();
            }
        });
        rvDouble.setAdapter(sectionAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
        public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
            super(layoutResId, sectionHeadResId, data);
        }

        @Override
        protected void convertHead(BaseViewHolder helper, final MySection item) {
            helper.setText(R.id.header, item.header);
            helper.setVisible(R.id.more, item.isMore());
            helper.addOnClickListener(R.id.more);
        }


        @Override
        protected void convert(BaseViewHolder helper, MySection item) {
            Video video = (Video) item.t;
            switch (helper.getLayoutPosition() %
                    2) {
                case 0:
                    helper.setImageResource(R.id.iv, R.mipmap.ic_launcher);
                    break;
                case 1:
                    helper.setImageResource(R.id.iv, R.mipmap.ic_launcher_round);
                    break;

            }
            helper.setText(R.id.tv, video.getName());
        }
    }

}
