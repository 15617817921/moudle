package com.liu.anew.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.liu.anew.R;
import com.lzy.ninegrid.NineGridView;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader
// implements ImageLoader {
//    @Override
//    public void onDisplayImage(Context context, ImageView imageView, String url) {
//        //占位符
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.ic_default_image)
//                .error(R.drawable.ic_default_image)
//                .override(200, 100)//200*100像素
//                .override(Target.SIZE_ORIGINAL)//原始尺寸
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用 硬盘缓存
//        // DiskCacheStrategy.NONE： 表示不缓存任何内容。
////        DiskCacheStrategy.DATA： 表示只缓存原始图片。
////        DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
////        DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
////        DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
//                .skipMemoryCache(true);//禁用内存缓存，默认开启
//        Glide.with(context)
////                .asBitmap()//指定静态图片 动态图显示第一帧
//                .load(url)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .apply(options)
//                .into(imageView);
//
//
////       回调与监听   、、、、、、、、
////        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
////            @Override
////            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
////                imageView.setImageDrawable(resource);
////            }
////        };
//    }
//
//    @Override
//    public Bitmap getCacheImage(String url) {
//        return null;
//    }
//}
 extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */


        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);

        //Picasso 加载图片简单用法
//        Picasso.with(context).load(path).into(imageView);

        //用fresco加载图片简单用法，记得要写下面的createImageView方法
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//        return simpleDraweeView;
        return null;
    }
}
