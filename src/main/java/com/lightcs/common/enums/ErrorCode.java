package com.lightcs.common.enums;

/**
 * @Author: peak-like
 * @Description:TODO
 * @DateTime: 2024/11/26 15:31
 **/
public enum ErrorCode {
    PARAMS_ERROR(40000, "请求参数错误"),
    NO_AUTH_ERROR(40001,"无权限"),
    NOT_LOGIN_ERROR(40002,"用户未登录"),
    OPERATION_ERROR(40003,"操作失败"),
    NOT_FOUND_ERROR(40004,"未找到对应资源"),
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
