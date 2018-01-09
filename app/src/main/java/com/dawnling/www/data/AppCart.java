package com.dawnling.www.data;

import com.blankj.utilcode.util.CacheUtils;
import com.dawnling.www.bean.CartEvent;
import com.dawnling.www.bean.ProductListBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 楼管物资补充 商品缓存类
 */
public class AppCart {
    //购物车
    private static List<ProductListBean> mList;

    public static List<ProductListBean> getCartList() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (mList.isEmpty()) {
            mList = (List<ProductListBean>) CacheUtils.getInstance().getSerializable("cacheCart");
            if (mList == null) {
                mList = new ArrayList<>();
            }
        }
        return mList;
    }

    /**
     * 单个商品数量变化 先改变后传入
     */
    public static void onChangeOne(ProductListBean bean) {
        if (bean == null) {
            return;
        }
        if (mList == null) {
            getCartList();
        }
        int index;
        if (mList.contains(bean)) {
            index = mList.indexOf(bean);
            mList.get(index).buy_num = bean.buy_num;
            if (mList.get(index).buy_num <= 0) {
                mList.remove(bean);
            }
        } else {
            mList.add(0, bean);
        }
        putCartList(mList);
    }

    /**
     * 获得购物车数量
     */
    public static int getCartNumber() {
        if (getCartList()==null || getCartList().isEmpty()) {
            return 0;
        }
        int number = 0;
        for (ProductListBean productBean : mList) {
            number += productBean.buy_num;
        }
        return number;
    }

    /**
     * 写入缓存
     */
    public static void putCartList(List<ProductListBean> cartList) {
        mList = cartList;
        //发送广播
        EventBus.getDefault().post(new CartEvent());
    }

    /**
     * 移除一样商品
     */
    public static void onRemove(ProductListBean bean) {
        if (bean == null) {
            return;
        }
        bean.buy_num = 0;
        mList.remove(bean);
        putCartList(mList);
    }

    /**
     * 清除补货清单
     */
    public static void clear() {
        if (getCartList()==null || getCartList().isEmpty()) {
            return;
        }
        CacheUtils.getInstance().remove("cacheCart");
        mList.clear();
    }

    /**
     * 比对数据
     * @param list
     * @return true有比对 false无比对
     */
    public static boolean comparison(List<ProductListBean> list) {
        List<ProductListBean> listCart = getCartList();
        if (list == null || list.size() == 0) {
            return false;
        }
        if (listCart == null || listCart.size() == 0) {
            for (ProductListBean productBean : list) {
                productBean.buy_num = 0;
            }
            return true;
        }
        int index;
        for (ProductListBean productBean : list) {
            if (listCart.contains(productBean)) {
                index = listCart.indexOf(productBean);
                productBean.buy_num = listCart.get(index).buy_num;
            } else {
                productBean.buy_num = 0;
            }
        }
        return true;
    }
}

