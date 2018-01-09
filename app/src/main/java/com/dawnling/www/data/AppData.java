package com.dawnling.www.data;

import com.dawnling.www.retorfit.CallBackAdapter;
import com.dawnling.www.retorfit.CallbackCenter;
import com.dawnling.www.retorfit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by LXL on 2017/12/13.
 */

public class AppData {

    public Call getData(CallBackAdapter callBackAdapter) {
        Map<String, Object> map = new HashMap<String, Object>();
        Call call = RetrofitClient.post(map);
        call.enqueue(new CallbackCenter(callBackAdapter));
        return call;
    }
}
