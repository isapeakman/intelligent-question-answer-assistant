package com.lightcs.common.result;

/**
 * @Author: MR.WU
 * @DateTime: 2024/11/26 10:29
 **/

import lombok.Data;

/**
 * 统一返回类型
 */
@Data
public class BaseResponse<T> {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
