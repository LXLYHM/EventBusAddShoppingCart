package com.dawnling.www.retorfit;

/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 */
public interface CallBackAdapter<T>{

    void onSuccess(T object);

    void onFailure(int code, String message);

    void onCompleted();
}
