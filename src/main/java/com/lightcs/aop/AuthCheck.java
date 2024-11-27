package com.lightcs.aop;

import com.lightcs.annotation.CheckAuth;
import com.lightcs.enums.ErrorCode;
import com.lightcs.enums.UserRoleEnum;
import com.lightcs.exception.BusinessException;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.service.UserService;
import com.lightcs.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.lightcs.enums.UserRoleEnum.*;

/**
 * @Author: peak-like
 * @Description: 鉴权切面
 * @DateTime: 2024/11/27 11:21
 **/
@Component
@Aspect
public class AuthCheck {
    @Autowired
    private UserService userService;

    /**
     *    * 前置通知
     * @param checkAuth 注解
     */
    @Before(value = "@annotation(checkAuth)")
    public void check(CheckAuth checkAuth){
        UserRoleEnum mustRoleEnum = checkAuth.mustRole();
        // 无权限要求，直接放行
        if(mustRoleEnum==null){
            return;
        }

        HttpServletRequest request = ServletUtils.getRequest();
        // 验证登录状态
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        String userRole = loginUserVO.getUserRole();
        UserRoleEnum userRoleEnum = getEnumByValue(userRole);

        // 如果被封号，直接拒绝
        if (BAN==(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 管理员权限校验
        if(ADMIN==mustRoleEnum){
            if(ADMIN!=userRoleEnum){
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
    }

}
