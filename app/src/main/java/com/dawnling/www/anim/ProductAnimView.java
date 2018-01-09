package com.dawnling.www.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 商品加入购物车动画 列表加入动画
 */
public class ProductAnimView extends android.support.v7.widget.AppCompatImageView {

    //小红点开始坐标
    Point mCircleStartPoint = new Point();
    //小红点结束坐标
    Point mCircleEndPoint = new Point();
    //小红点控制点坐标
    Point mCircleConPoint = new Point();

    public ProductAnimView(Context context) {
        super(context);
    }

    /**
     * 设置开始点和移动点
     *
     * @param x
     * @param y
     */
    public void setCircleStartPoint(int x, int y) {
        this.mCircleStartPoint.x = x;
        this.mCircleStartPoint.y = y;
    }

    /**
     * 设置结束点
     *
     * @param x
     * @param y
     */
    public void setCircleEndPoint(int x, int y) {
        this.mCircleEndPoint.x = x;
        this.mCircleEndPoint.y = y;
    }

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mCircleStartPoint == null || mCircleEndPoint == null) {
            return;
        }
        //设置控制点
        mCircleConPoint.x = ((mCircleStartPoint.x + mCircleEndPoint.x) / 2);
        mCircleConPoint.y = (20);
        //设置值动画
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CirclePointEvaluator(), mCircleStartPoint, mCircleEndPoint);
        valueAnimator.setDuration(600);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point viewPoint = (Point) animation.getAnimatedValue();
//                mCircleMovePoint.x = viewPoint.x;
//                mCircleMovePoint.y = viewPoint.y;
//                invalidate();
//                LogUtils.e("距离:"+mCircleEndPoint.x+"\nviewPoint.x："+viewPoint.x+"::mCircleStartPoint.x:"+mCircleStartPoint.x);
                float scale =  1f - ((float)viewPoint.x-mCircleStartPoint.x)/(mCircleEndPoint.x-mCircleStartPoint.x) +0.1f;
                setScaleX(scale);
                setScaleY(scale);
                setX(viewPoint.x);
                setY(viewPoint.y);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.removeView(ProductAnimView.this);
            }
        });
        valueAnimator.start();
    }


    /**
     * 自定义Evaluator
     */
    public class CirclePointEvaluator implements TypeEvaluator {
        /**
         * @param t          当前动画进度
         * @param startValue 开始值
         * @param endValue   结束值
         * @return
         */
        @Override
        public Object evaluate(float t, Object startValue, Object endValue) {

            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;

            int x = (int) (Math.pow((1 - t), 2) * startPoint.x + 2 * (1 - t) * t * mCircleConPoint.x + Math.pow(t, 2) * endPoint.x);
            int y = (int) (Math.pow((1 - t), 2) * startPoint.y + 2 * (1 - t) * t * mCircleConPoint.y + Math.pow(t, 2) * endPoint.y);
            return new Point(x, y);
        }
    }
}
