package com.liu.anew.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.liu.anew.application.MyApplication;
import com.lzy.ninegrid.NineGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/** UniversalImageLoader加载 */
public class UniversalImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        ImageLoader.getInstance().displayImage(url, imageView, MyApplication.imageLoaderOptions);
//        ImageLoader.getInstance().displayImage(url, imageView,  MyApplication.imageLoaderOptions, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//
//            }
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        }, new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//
//            }
//        });
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
