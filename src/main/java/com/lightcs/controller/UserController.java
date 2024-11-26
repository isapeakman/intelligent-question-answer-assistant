package com.lightcs.controller;

import com.lightcs.common.BaseResponse;
import com.lightcs.common.ResultBuilder;
import com.lightcs.enums.ErrorCode;
import com.lightcs.exception.BusinessException;
import com.lightcs.model.dto.user.UserRegisterRequest;
import com.lightcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: peak-like
 * @Description: 用户控制层
 * @DateTime: 2024/11/25 11:59
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    @ResponseBody
    public BaseResponse<Long> userRegister(@Validated @RequestBody UserRegisterRequest registerRequest){
        String userPassword = registerRequest.getUserPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        // 校验密码
        if(!userPassword.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }
        Long userId = userService.userRegister(registerRequest.getUserAccount(), userPassword);
        return  ResultBuilder.success(userId);
    }

}
