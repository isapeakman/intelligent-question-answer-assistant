package com.lightcs.model.dto.user;

import com.lightcs.model.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: peak-like
 * @Description: 用户查询请求
 * @DateTime: 2024/11/27 9:58
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;
    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
}
