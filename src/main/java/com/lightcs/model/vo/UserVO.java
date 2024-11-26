package com.lightcs.model.vo;

/**
 * @Author: MR.WU
 * @DateTime: 2024/11/26 10:33
 **/

import lombok.Data;

/**
 * 用户视图类
 */
@Data
public class UserVO {
    private String userAccount;
    private String username;
    private String userAvatar;
    private String userProfile;
    private String userRole;
}
