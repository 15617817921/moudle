package com.liu.anew.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.widget.ImageView;

import com.liu.anew.R;
import com.liu.anew.common.CrashHandler;
import com.liu.anew.imageloader.PicassoImageLoader;
import com.liu.anew.receiver.NetStateReceiver;
import com.liu.anew.utils.ConstantsUtil;
import com.liu.anew.utils.MyLogger;
import com.liu.anew.utils.MyToast;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.xutils.image.ImageOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import okhttp3.OkHttpClient;


/**
 * Created by youxi on 2016-9-12.
 */
public class MyApplication extends Application {
    private static MyApplication singleton;
    public static int i = 0;
    public static Context mContext;
    //表示是否连接
    public static boolean isConnected;
    //表示网络类型（移动数据或者wifi）移动：Moblie Wifi:Wifi
    public static String netWorkState;

//    DisplayImageOptions options = new DisplayImageOptions.Builder()
//
//            .showImageOnLoading(R.drawable.ic_stub)// 设置图片下载期间显示的图片
//
//            .showImageForEmptyUri(R.drawable.ic_empty)// 设置图片Uri为空或是错误的时候显示的图片
//
//            .showImageOnFail(R.drawable.ic_error)// 设置图片加载或解码过程中发生错误显示的图片
//
//            .resetViewBeforeLoading(false) // default 设置图片在加载前是否重置、复位
//
//            .delayBeforeLoading(1000) // 下载前的延迟时间
//
//            .cacheInMemory(false)// default  设置下载的图片是否缓存在内存中
//
//            .cacheOnDisk(false)// default  设置下载的图片是否缓存在SD卡中
//
//            .preProcessor(...)
//
//            .postProcessor(...)
//
//        .extraForDownloader(...)
//
//        .considerExifParams(false)// default
//
//        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default 设置图片以如何的编码方式显示
//
//        .bitmapConfig(Bitmap.Config.ARGB_8888)// default 设置图片的解码类型
//
//        .decodingOptions(...) // 图片的解码设置
//
//        .displayer(newSimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
//
//            .handler(newHandler()) // default
//
//            .build();
    public static DisplayImageOptions imageLoaderOptions = new DisplayImageOptions.Builder()//
            .showImageOnLoading(R.drawable.ic_default_image)    //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.ic_default_image)  //设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.ic_default_image)       //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .build();                                           //构建完成

    public static ImageOptions xUtilsOptions = new ImageOptions.Builder()//
            .setIgnoreGif(false)                                //是否忽略GIF格式的图片
            .setImageScaleType(ImageView.ScaleType.FIT_CENTER)  //缩放模式
            .setLoadingDrawableId(R.drawable.ic_default_image)  //下载中显示的图片
            .setFailureDrawableId(R.drawable.ic_default_image)  //下载失败显示的图片
            .build();                                           //得到ImageOptions对象
    private MyToast toast;

    @Override
    public void onCreate() {
//        initanr();
        super.onCreate();
        singleton = this;
        mContext = getApplicationContext();
        toast = new MyToast(this);
        init();
//        refresh();
    }

    /**
     * 所有的初始化
     */
    private void init() {
        BGASwipeBackHelper.init(this, null);//滑动结束页面
        NineGridView.setImageLoader(new PicassoImageLoader());//九宫格初始化
        initNetWorkReceiver();
        initCaught();
        initOkGo();
    }
    private boolean DEVELOPER_MODE = true;
    private void initanr() {
//        penaltyDeathOnNetwork()，当触发网络违规时，Crash掉当前应用程序。
//        penaltyDialog()，触发违规时，显示对违规信息对话框。
//        penaltyFlashScreen()，会造成屏幕闪烁，不过一般的设备可能没有这个功能。
//        penaltyDropBox()，将违规信息记录到 dropbox 系统日志目录中（/data/system/dropbox），你可以通过如下命令进行插件：
//        adb shell dumpsys dropbox dataappstrictmode  --print
        if (DEVELOPER_MODE) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
//                    .penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
//                    .build());
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build());
//                                .penaltyDeath();//检查整个过程。
//            penaltyDeathOnNetwork()：检查在任何网络使用情况下崩溃过程。
//            penaltyDialog()：检测到的错误行为时向开发者显示一个讨厌的对话框。
//            penaltyFlashScreen()：检查到问题时闪烁屏幕。
//            penaltyLog()：将检测到的问题情况记录到系统日志中。

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects() //探测SQLite数据库操作
                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog() //打印logcat
                    .penaltyDeath()//当触发违规条件时，直接Crash掉当前应用程序。
                    .build());
        }
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)                   //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)  ;                             //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    private void initCaught() {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(); //在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
    }


    private void refresh() {
        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                //指定为经典Header，默认是 贝塞尔雷达Header
//                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
//            }
//        });
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
//            }
//        });
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
//            @NonNull
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                BezierRadarHeader header = new BezierRadarHeader(context);
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
//                return header;
//            }
//        });
    }
    // 全局的 handler 对象
    private final Handler appHandler = new Handler();
    public static MyApplication getInstance() {
        return singleton;
    }
    public void toastShowByBuilder(final MyToast.Builder builder) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            appHandler.post(new Runnable() {
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
    /**
     * 网络状态广播注册
     *
     * @author hjy
     * created at 2016/12/12 15:30
     */
    private void initNetWorkReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        NetStateReceiver mNetWorkReceiver = new NetStateReceiver(netWorkHandler);
        registerReceiver(mNetWorkReceiver, filter);
    }

    //网络监听Handler
    private Handler netWorkHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantsUtil.NetWork.IS_WIFI:
                    netWorkState = ConstantsUtil.NetWork.WIFI;
                    isConnected = true;
                    MyLogger.kLog().e("IS_WIFI");
                    break;
                case ConstantsUtil.NetWork.IS_MOBILE:
                    netWorkState = ConstantsUtil.NetWork.MOBLIE;
                    isConnected = true;
                    break;
                case ConstantsUtil.NetWork.NO_CONNECTION:
                    MyLogger.kLog().e("NO_CONNECTION");
                    isConnected = false;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 图片加载配置
     */

    private void initUniversalImageLoader() {
        File cacheDir = StorageUtils.getCacheDirectory(mContext);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)

                .memoryCacheExtraOptions(480, 800)// default = device screen dimensions 内存缓存文件的最大长宽

//            .diskCacheExtraOptions(480,800,null) // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个

//            .taskExecutor()

//        .taskExecutorForCachedImages()

                .threadPoolSize(3)// default  线程池内加载的数量

                .threadPriority(Thread.NORM_PRIORITY - 2)// default 设置当前线程的优先级

                .tasksProcessingOrder(QueueProcessingType.FIFO)// default

                .denyCacheImageMultipleSizesInMemory()

                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//可以通过自己的内存缓存实现

                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值

                .memoryCacheSizePercentage(13)// default

//            .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径

                .diskCacheSize(50 * 1024 * 1024)// 50 Mb sd卡(本地)缓存的最大值

                .diskCacheFileCount(100) // 可以缓存的文件数量

                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密

                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())

                .imageDownloader(new BaseImageDownloader(mContext)) // default

                .imageDecoder(new BaseImageDecoder(true)) // default

                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default

                .writeDebugLogs()// 打印debug log

                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }
}
