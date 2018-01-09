package com.dawnling.www.bean;

import android.view.View;

/**
 * 商品加入购物车动画
 */
public class ProductAnimEvent {

    public View view;
    public String imgPath;

    public ProductAnimEvent(View view, String imgPath) {
        this.view = view;
        this.imgPath = imgPath;
    }
}
