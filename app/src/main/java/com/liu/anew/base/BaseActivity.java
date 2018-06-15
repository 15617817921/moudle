package com.liu.anew.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.liu.anew.application.MyApplication;
import com.liu.anew.common.ActivityManager;
import com.liu.anew.common.MyActivityManager;
import com.liu.anew.utils.MyLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


//import rx.Subscription;
//import rx.subscriptions.CompositeSubscription;


/**
 * Created by Administrator on 2016/5/6.
 */
public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {
    //上次按下返回键的系统时间
    protected long lastBackTime = 0;
    protected Context mContext;
    protected MyLogger log;

    protected ExecutorService singleThread = Executors.newSingleThreadExecutor();
    protected ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//可缓存线程池
    /**
     * 所有已存在的Activity
     */
    protected static ConcurrentLinkedQueue<Activity> allActivity = new ConcurrentLinkedQueue<Activity>();
    /**
     * 同时有效的界面数量
     */
    protected static final int validActivityCount = 15;


    private MyApplication app;
    protected ProgressDialog dialog;

    protected BGASwipeBackHelper mSwipeBackHelper;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(setLayoutId());
        ActivityManager.getInstance().add(this);
        init();
        try {//设置布局文件、初始化布局文件中的控件、初始化控件的监听、进行数据初始化。（子类重写这些方法）
            initView();
            initData();
            initListener();
        } catch (Exception e) {
            log.e(e.getMessage());
        }

    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
//        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }


    /**
     * 初始化
     */
    private void init() {
        mContext = this;
        log = MyLogger.kLog();
        unbinder = ButterKnife.bind(this);
        //Activity队列管理，如果超出指定个数，获取并移除此队列的头（队列中时间最长的）。
        if (allActivity.size() >= validActivityCount) {
            Activity act = allActivity.poll();
            act.finish();// 结束
        }
        allActivity.add(this);
    }

    public abstract int setLayoutId();

    public void initView() {

    }

    public void initData() {

    }

    public void initListener() {

    }

    public void initDialog() {
        dialog = new ProgressDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);//响应取消操作，这里如果设置false,按返回键ProgressDialog也不消失。
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("");
//        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      unbinder.unbind();
        MyActivityManager.removeActivity(this);
    }

    public void gotoAtivity(Class clazz, Bundle bundle) {
        Intent it = new Intent(this, clazz);
        if (bundle != null) {
            it.putExtra("bundle", bundle);
        }
        startActivity(it);
    }


    @SuppressWarnings("unchecked")
    protected final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    protected final <E extends View> E getView(int id) {
        try {
            return (E) this.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    public static String getPath() {
        File appDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "image");
        if (!appDir.exists()) {
            // 目录不存在 则创建
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";

        return fileName;
    }

    /**
     * @param context
     * @param bitmap
     */
    public static void saveImageToGallery(Context context, Bitmap bitmap, String imagePath) {
        File appDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "image");
        if (!appDir.exists()) {
            // 目录不存在 则创建
            appDir.mkdirs();
        }
//        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, imagePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 保存bitmap至本地
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ScannerByReceiver(context, file.getAbsolutePath());
            if (!bitmap.isRecycled()) {
                bitmap.recycle(); //当存储大图片时，为避免出现OOM ，及时回收Bitmap
                System.gc(); // 通知系统回收
            }
            // Toast.makeText(context, "图片保存成功" ,
            // Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiver扫描更新图库图片
     **/
    public static void ScannerByReceiver(Context context, String path) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + path)));
    }

    public void showLoading(String content) {
        if (dialog != null && dialog.isShowing()) return;
        dialog = new ProgressDialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(content);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void hitKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }


    /**
     * //1 男  2 女 3 不详
     *
     * @param zifu
     * @return
     */
    public String isSex(String zifu) {
        String sex = "";
        if (zifu.equals("1")) {
            sex = "男";
        } else if (zifu.equals("2")) {
            sex = "女";
        } else if (zifu.equals("3")) {
            sex = "不详";
        }
        return sex;
    }
}
