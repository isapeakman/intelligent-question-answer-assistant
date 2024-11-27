package com.lightcs.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: peak-like
 * @Description: 用户增加请求
 * @DateTime: 2024/11/27 9:32
 **/
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
