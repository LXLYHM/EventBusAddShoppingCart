package com.dawnling.www.retorfit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LXL on 2017/12/8.
 * http://my.csdn.net/lxlyhm
 * https://github.com/LXLYHM
 * http://www.jianshu.com/u/8fd63a0d4c4c
 */
public interface Api {

    @FormUrlEncoded
    @POST("Products.java")
    Call<ResponseBody> post(@FieldMap Map<String,Object> paramsMap);
}
