package com.dawnling.www.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.dawnling.www.retorfit.RetrofitClient;

/**
 * Created by LXL on 2017/12/13.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        RetrofitClient.init(this);
    }
}
