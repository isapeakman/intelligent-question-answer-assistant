package com.lightcs.common.annotation;

import com.lightcs.common.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: peak-like
 * @Description: 鉴权注解
 * @DateTime: 2024/11/27 11:27
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuth {
    UserRoleEnum mustRole() default UserRoleEnum.USER;
}
