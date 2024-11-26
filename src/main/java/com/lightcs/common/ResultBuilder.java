package com.lightcs.common;

import cn.hutool.http.HttpStatus;

/**
 * @Author: peak-like
 * @Description: 结果构造器
 * @DateTime: 2024/11/26 11:25
 **/
public class ResultBuilder{
    private static final String SUCCESS = "success";
    private ResultBuilder() {
    }

    public static synchronized<T>BaseResponse<T> success(T data){
        return new BaseResponse<T>(HttpStatus.HTTP_OK,data,SUCCESS);
    }
}
