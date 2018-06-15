package com.liu.anew.activity.my;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liu.anew.R;
import com.liu.anew.base.BaseActivity;
import com.liu.anew.base.BaseRecyclerAdapter;
import com.liu.anew.base.SmartViewHolder;
import com.liu.anew.bean.data.EvaluationItem;
import com.liu.anew.bean.data.EvaluationPic;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

public class NineActivity extends BaseActivity {

    @BindView(R.id.rv_nine)
    RecyclerView rvNine;
    private BaseRecyclerAdapter mAdapter;
    private String[] image = new String[]{"http://img.taopic.com/uploads/allimg/110729/1830-110H916410651.jpg",
            "http://pic10.photophoto.cn/20090224/0036036802407491_b.jpg",
            "http://pic22.photophoto.cn/20120113/0036036848061774_b.jpg",
            "http://img.taopic.com/uploads/allimg/140804/240388-140P40P33417.jpg",
            "http://pic27.photophoto.cn/20130630/0036036877482236_b.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_nine;
    }

    @Override
    public void initView() {
        rvNine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNine.setItemAnimator(new DefaultItemAnimator());
        rvNine.setAdapter(mAdapter = new BaseRecyclerAdapter<EvaluationItem>(loadModels(), R.layout.item_image_rv) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, EvaluationItem bean, int position) {
                holder.text(R.id.tv_name,bean.getUserName());
                NineGridView nineGrid = (NineGridView) holder.findViewById(R.id.nineGrid);

                List<ImageInfo> imageInfo = new ArrayList<>();
                List<EvaluationPic> imageDetails = bean.getAttachments();
                if (imageDetails != null) {
                    for (EvaluationPic imageDetail : imageDetails) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(imageDetail.imageUrl);
                        info.setBigImageUrl(imageDetail.imageUrl);
                        imageInfo.add(info);
                    }
                }
                nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
            }
        });
    }

    private List<EvaluationItem> loadModels() {
        List<EvaluationItem> listdata = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EvaluationItem bean = new EvaluationItem();
            bean.setUserName("name" + i);
            List<EvaluationPic> list = new ArrayList<>();
            for (int j = 0; j < image.length; j++) {
                EvaluationPic picbean = new EvaluationPic();
                picbean.setImageUrl(image[j]);
                list.add(picbean);
            }
            bean.setAttachments(list);
            listdata.add(bean);
        }
        return listdata;
    }
}
