package com.liu.anew.fragment.two;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.anew.R;
import com.liu.anew.adapter.TypeLeftAdapter;
import com.liu.anew.adapter.TypeRightAdapter;
import com.liu.anew.base.BaseFragment;
import com.liu.anew.bean.data.TypeBean;
import com.liu.anew.utils.JsonUtil;


import java.util.ArrayList;
import java.util.List;


/**
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseFragment {
    private FrameLayout fl_list_container;
    private ListView lv_left;
    private RecyclerView rv_right;
    private List<TypeBean.ResultBean> result = new ArrayList<>();

//    private String[] urls = new String[]{Constants.SKIRT_URL, Constants.JACKET_URL, Constants.PANTS_URL, Constants.OVERCOAT_URL,
//            Constants.ACCESSORY_URL, Constants.BAG_URL, Constants.DRESS_UP_URL, Constants.HOME_PRODUCTS_URL, Constants.STATIONERY_URL,
//            Constants.DIGIT_URL, Constants.GAME_URL};
//
//

    private TypeLeftAdapter leftAdapter;
    private boolean isFirst = true;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_list, null);
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        rv_right = (RecyclerView) view.findViewById(R.id.rv_right);
        return view;
    }

    @Override
    public void initData() {
        processData();
        if (isFirst) {
            leftAdapter = new TypeLeftAdapter(mContext);
            lv_left.setAdapter(leftAdapter);
        }
        initListener(leftAdapter);
        //解析右边数据
        TypeRightAdapter rightAdapter = new TypeRightAdapter(mContext, result);
        rv_right.setAdapter(rightAdapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        rv_right.setLayoutManager(manager);
    }

    private void processData() {
        String json = JsonUtil.getJson(mContext, "typejson.json");
        log.e(json);
        JSONObject object = JSON.parseObject(json);
        String ss = object.getString("result");
        log.e(ss);
        result  = JSON.parseArray(ss, TypeBean.ResultBean.class);
        log.e(result.size()+"--"+json);
    }

    private void initListener(final TypeLeftAdapter adapter) {
        //点击监听
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
                if (position != 0) {
                    isFirst = false;
                }
//                getDataFromNet(urls[position]);
                leftAdapter.notifyDataSetChanged();
            }
        });

        //选中监听
        lv_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);//刷新
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}



