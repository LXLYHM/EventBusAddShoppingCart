package com.dawnling.www.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.dawnling.www.R;
import com.dawnling.www.adapter.ProductAdapter;
import com.dawnling.www.bean.CartEvent;
import com.dawnling.www.bean.ProductListBean;
import com.dawnling.www.data.AppCart;
import com.dawnling.www.data.AppData;
import com.dawnling.www.retorfit.CallBackAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 * 购物车
 */
public class MainActivity extends AddCartActivity {

    private RecyclerView mRecyclerView;
    private List<ProductListBean> mList = new ArrayList();
    private ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //状态栏全局默认统一成白底黑字
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).init();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProductAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mList);

        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        new AppData().getData(new CallBackAdapter<String>() {
            @Override
            public void onSuccess(String object) {
                List<ProductListBean> list = new Gson().fromJson(object, new TypeToken<List<ProductListBean>>() {}.getType());
                AppCart.comparison(list);
                mList = list;
                mAdapter.setNewData(mList);
            }

            @Override
            public void onFailure(int code, String message) {
            }

            @Override
            public void onCompleted() {
            }
        });
    }

    /**
     * 显示状态
     */
    public void checkState() {
        if (AppCart.getCartNumber() <= 0) {
            if (qBadgeView != null) {
                qBadgeView.hide(true);
            }
        } else {
            //动画
            if (qBadgeView == null) {
                View view = findViewById(R.id.containerBageView);
                qBadgeView = new QBadgeView(this);
                qBadgeView.setGravityOffset(0f, ConvertUtils.dp2px(6f), false).bindTarget(view);
            }
            qBadgeView.setBadgeNumber(AppCart.getCartNumber());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CartEvent event) {
        checkState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppCart.clear();//清除
        ImmersionBar.with(this).destroy();
    }
}
