package com.liu.anew.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.liu.anew.R;
import com.lzy.ninegrid.NineGridView;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Picasso.get().load(url)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
