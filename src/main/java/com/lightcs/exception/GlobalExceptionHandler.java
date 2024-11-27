package com.lightcs.exception;

import com.lightcs.common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: peak-like
 * @Description: 全局异常拦截器
 * @DateTime: 2024/11/26 11:14
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  参数校验异常拦截器
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleNotValidException(MethodArgumentNotValidException e) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        map.put("data", Collections.emptyMap());
        map.put("message",e.getBindingResult().getFieldError().getDefaultMessage());
        return map;
    }

    /**
     *  业务异常拦截器
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Map<String,Object> handleBusinessException(BusinessException e) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", e.getCode());
        map.put("data", Collections.emptyMap());
        map.put("message",e.getMessage());
        return map;
    }
}
