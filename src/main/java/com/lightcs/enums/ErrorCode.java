package com.lightcs.enums;

/**
 * @Author: peak-like
 * @Description:TODO
 * @DateTime: 2024/11/26 15:31
 **/
public enum ErrorCode {
    PARAMS_ERROR(40000, "请求参数错误"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
