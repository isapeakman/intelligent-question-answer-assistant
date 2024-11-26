package com.lightcs.controller;

import com.lightcs.common.BaseResponse;
import com.lightcs.common.ResultBuilder;
import com.lightcs.enums.ErrorCode;
import com.lightcs.exception.BusinessException;
import com.lightcs.model.dto.user.UserLoginRequest;
import com.lightcs.model.dto.user.UserRegisterRequest;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.model.vo.UserVO;
import com.lightcs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@Validated @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        UserVO userVO = userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), request);
        return  ResultBuilder.success(userVO);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        boolean result = userService.userLogout(request);
        return ResultBuilder.success(result);
    }
    @GetMapping("/current")
    public BaseResponse<LoginUserVO> current(HttpServletRequest request){
        LoginUserVO currentLoginUser = userService.getCurrentLoginUser(request);
        return  ResultBuilder.success(currentLoginUser);
    }
}
