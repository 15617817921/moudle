package com.liu.anew.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.anew.utils.MyLogger;
import com.liu.anew.utils.MyToast;


/**
 * Fragment基类
 * Created by qwp on 2016/5/6.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private ProgressDialog dialog;
    protected MyLogger log;
    private MyToast toast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }
    /**
     * 有子类实现，实现特有效果
     * @return
     */
    public abstract View initView();

    public void initData() {

    }
    public void initListener() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        log = MyLogger.kLog();
        toast=new MyToast(mContext);
    }

    protected final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    public void start_activity(Intent intent) {
        startActivity(intent);
    }

    public void gotoAtivity(Class clazz, Bundle bundle) {
        Intent it = new Intent(getActivity(), clazz);
        if (bundle != null) {
            it.putExtra("bundle", bundle);
        }
        startActivity(it);
    }
    public void showToast(final MyToast.Builder builder) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    toast.toastShow(builder);
                    MyLogger.kLog().e("1");
                }
            });
        } else {
            MyLogger.kLog().e("2");
            toast.toastShow(builder);
        }
    }
}
