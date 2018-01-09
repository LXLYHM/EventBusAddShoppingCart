package com.dawnling.www.retorfit;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dawnling.www.retorfit.base.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 */
public class CallbackCenter implements Callback<ResponseBody> {

    private CallBackAdapter callBackAdapter;

    public CallbackCenter(CallBackAdapter callBackAdapter) {
        this.callBackAdapter = callBackAdapter;
    }

    private void fromJson(Response<ResponseBody> response) {
        if (callBackAdapter == null) {
            //不求回报
            return;
        }
        try {
            LogUtils.e("请求返回结果:"+response.code()+":"+response.message());
            if (response.code() == 200 && response.body() != null) {
                String json = response.body().string();
                if (!TextUtils.isEmpty(json)) {
                    json = json.replace("\\n", "\n");
                    json = json.replace("\\r", "\r");
                    json = json.replace("\\t", "\t");
                    json = json.replace("\\f", "\f");
                    json = json.replace("\\b", "\b");
                    LogUtils.e("请求返回结果:" + json);
                    BaseResponse<Object> data = new Gson().fromJson(json, new TypeToken<BaseResponse<Object>>() {
                    }.getType());
                    if (data.error_code == 0) {
                        onSuccess(data.data);
                        return;
                    } else {
                        //统一处理
                        if (data.error_code >= 10000 && data.error_code < 20000) {
                            //1开头的显示网络异常
                            ToastUtils.showShort("网络异常");
                            onFailure(data.error_code, data.msg);
                        } else if (data.error_code >= 20000 && data.error_code < 30000) {
                            //2开头进行逻辑处理
                            onFailure(data.error_code, data.msg);
                            return;
                        } else if (data.error_code >= 30000 && data.error_code < 40000) {
                            //3开头直接显示msg内容
                            ToastUtils.showShort(data.msg);
                            onFailure(data.error_code, data.msg);
                        } else {
                            //测试时候用
                            ToastUtils.showShort("" + data.msg);
                        }
                        onCompleted();
                        return;
                    }
                }
            }
            //todo 正式版应该保留
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("请求结果出现异常:" + e.toString());
        }
        onFailure(0, "");
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        fromJson(response);
    }

    /**
     * 请求失败代码规范（前端自己规定
     * 204 没有网络
     */
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        LogUtils.e("请求返回错误:" + t.getMessage() + ":" + t.fillInStackTrace() + ":" + t.getCause());
        Throwable throwable = t.fillInStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            //连接超时
            ToastUtils.showShort("连接超时");
            onFailure(ErroeCode.CODE103, t.getMessage());
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            //没有网络
            ToastUtils.showShort("没有网络");
            onFailure(ErroeCode.CODE204, t.getMessage());
        } else if (throwable instanceof SocketException) {
            //用户取消操作
            ToastUtils.showShort("取消操作");
            onFailure(ErroeCode.CODECANCEL, t.getMessage());
        } else {
            ToastUtils.showShort("请求失败，请检查您的网络");
            onFailure(0, t.getMessage());
        }

    }

    void onSuccess(Object data) {
        String json = new Gson().toJson(data);
        if (!TextUtils.isEmpty(json)) {
            callBackAdapter.onSuccess(json);
        }
        callBackAdapter.onCompleted();
    }

    void onFailure(int code, String message) {
        callBackAdapter.onFailure(code, message + "");
        callBackAdapter.onCompleted();
    }

    void onCompleted() {
        callBackAdapter.onCompleted();
    }
}

