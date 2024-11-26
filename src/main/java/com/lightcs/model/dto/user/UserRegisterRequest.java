package com.lightcs.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: MR.WU
 * @DateTime: 2024/11/26 10:40
 **/
@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;
    @NotBlank(message = "账户不为空!")
    private String userAccount;
    @NotBlank(message = "密码不为空!")
    private String userPassword;
    @NotBlank(message = "确认密码不为空!")
    private String confirmPassword;
}
