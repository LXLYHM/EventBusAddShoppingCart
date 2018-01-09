package com.dawnling.www.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dawnling.www.R;

/**
 * 图片加载器
 * github:https://github.com/KnifeStone/Hyena
 * Created by KnifeStone on 2017-6-9.
 */
public class ImageLoader {

    private static ImageLoader mInstance;

    private ImageLoader() {
    }

    //单例模式，节省资源
    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * 加载默认图片
     */
    public void load(String url, ImageView iv) {
        if (iv == null) return;
        load(url, iv, R.mipmap.ic_launcher_round);
    }

    /**
     * 加载默认图片,自定义占位图
     */
    public void load(String url, ImageView iv, int placeholderResId) {
        if (iv == null) return;
        Glide.with(iv.getContext())
                .load(url)
                .apply(rerequestOptions(placeholderResId))
                .into(iv);
    }

    /**
     * 加载圆图
     */
    public void loadRound(String url, final ImageView iv) {
        Glide.with(iv.getContext())
                .asBitmap()
                .load(url + "")
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(iv.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public RequestOptions rerequestOptions(int placeholderResId) {
        return new RequestOptions()
                .optionalCenterCrop()
                .placeholder(placeholderResId)
                .error(placeholderResId);
    }
}
