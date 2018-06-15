package com.liu.anew.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liu.anew.R;

public class LoadingDialog {

    private static Dialog dialog;


    //    public LoadingDialog(Context context, int layoutResID, String content) {
//        super(context, R.style.loading_dialog); //dialog的样式
//        this.context = context;
//        this.layoutResID = layoutResID;
//        this.content = content;
//        mConvertView = LayoutInflater.from(context).inflate(layoutResID, null);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        dialog = new Dialog(context, R.style.loading_dialog);
//        dialog.setContentView(mConvertView);
//        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        TextView msg = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
//        msg.setText(content);
//        dialog.show();
//
//        Window window = getWindow();
//        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
////        window.setWindowAnimations(RcyMoreAdapter.style.bottom_menu_animation); // 添加动画效果
//        setContentView(layoutResID);
//
//        WindowManager windowManager = ((Activity) context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
//        lp.height = (int) (display.getHeight());
//        getWindow().setAttributes(lp);
//        setCanceledOnTouchOutside(false);// 点击Dialog外部消失 false
//    }
    public LoadingDialog() {
    }

    public static Dialog hideLoading(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        return dialog;
    }


    public static Dialog showLoading(Context context, String content) {
//        dialog = new Dialog(context, R.style.loading_dialog);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_loading, null);
//        final int cFullFillWidth = 10000;
//        layout.setMinimumWidth(cFullFillWidth);
//
//        TextView msg = (TextView) layout.findViewById(R.id.id_tv_loadingmsg);
//        msg.setText(content);

        dialog = new Dialog(context,R.style.loading_dialog);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(true);//返回键  ture 取消
        dialog.setCanceledOnTouchOutside(false);// 点击Dialog外部消失 false不可取消
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(content);
        dialog.show();

        return dialog;
    }
}



