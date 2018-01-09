package com.dawnling.www.retorfit;

import android.content.Context;

import com.dawnling.www.R;

import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;

/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 * 请求管理
 */
public class RetrofitClient {

    private static Retrofit retrofit;

    public static void init(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())//解析方法;
                .build();
    }

    public static Call<ResponseBody> post(@FieldMap Map<String,Object> paramsMap) {
        return retrofit.create(Api.class).post(paramsMap);
    }
}
