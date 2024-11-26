package com.lightcs.exception;

import com.lightcs.enums.ErrorCode;

/**
 * @Author: peak-like
 * @Description: 业务异常
 * @DateTime: 2024/11/26 15:26
 **/
public class BusinessException extends RuntimeException{
    private int code;
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code ,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
