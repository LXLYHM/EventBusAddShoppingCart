package com.dawnling.www.ui;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ConvertUtils;
import com.dawnling.www.anim.ProductAnimView;
import com.dawnling.www.bean.ProductAnimEvent;
import com.dawnling.www.utils.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import q.rorbin.badgeview.QBadgeView;

/**
 * 补货时加入补货清单（购物车）效果基类
 * Created by LXL on 2017/12/11.
 */
public abstract class AddCartActivity extends AppCompatActivity {

    protected QBadgeView qBadgeView = null;
    protected ObjectAnimator animator = null;

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ProductAnimEvent event) {
        if (qBadgeView == null) {
            return;
        }
        View view = event.view;
        //获取商品坐标
        int[] goodsPoint = new int[2];
        view.getLocationInWindow(goodsPoint);
        //获取购物车坐标
        int[] shoppingCartPoint = new int[2];
        qBadgeView.getLocationInWindow(shoppingCartPoint);
        //生成商品View 并播放商品加入购物车动画
        ProductAnimView animView = new ProductAnimView(this);
        ImageLoader.getInstance().loadRound(event.imgPath, animView);
        animView.setCircleStartPoint(goodsPoint[0] + view.getWidth() / 2 - ConvertUtils.dp2px(24f), goodsPoint[1] + view.getHeight() / 2 - ConvertUtils.dp2px(24f));
        animView.setCircleEndPoint(shoppingCartPoint[0], shoppingCartPoint[1] - ConvertUtils.dp2px(24f));
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(animView, ConvertUtils.dp2px(48f), ConvertUtils.dp2px(48f));
        animView.startAnimation();
        //判断是否播放小红点跳跃动画
        if (animator == null && qBadgeView != null) {
            animator = ObjectAnimator.ofFloat(qBadgeView, "translationY", 0f, -ConvertUtils.dp2px(6f), 0f);
            animator.setDuration(300);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animator.isRunning()) {
                    return;
                }
                animator.start();
            }
        }, 500);
    }
}
