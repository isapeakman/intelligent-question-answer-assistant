package com.lightcs.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: peak-like
 * @Description: 用户登录
 * @DateTime: 2024/11/26 16:41
 **/
@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;
    @NotBlank(message = "账户不为空!")
    private String userAccount;
    @NotBlank(message = "密码不为空!")
    private String userPassword;
}