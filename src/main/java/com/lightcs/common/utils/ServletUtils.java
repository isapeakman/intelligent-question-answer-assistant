package com.lightcs.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: peak-like
 * @Description: servlet工具类
 * @DateTime: 2024/11/27 14:07
 **/
public class ServletUtils {
    private ServletUtils() {
    }
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
