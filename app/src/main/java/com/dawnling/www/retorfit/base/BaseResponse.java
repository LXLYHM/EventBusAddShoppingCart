package com.dawnling.www.retorfit.base;

import java.io.Serializable;

/**
 * Created by LXL on 2017-6-5.
 */
public class BaseResponse<T> implements Serializable {

    public int error_code; //0  成功
    public String msg;//返回消息
    public T data;
}
