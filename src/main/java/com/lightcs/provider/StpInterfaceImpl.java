package com.lightcs.provider;

/**
 * @Author: peak-like
 * @Description: sa-token自定义权限加载接口实现类
 * @DateTime: 2024/12/2 16:28
 **/
import cn.dev33.satoken.stp.StpInterface;
import com.lightcs.common.utils.ServletUtils;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private UserService userService;
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUserVO loginUserVO = userService.getLoginUserVO(ServletUtils.getRequest());
        String userRole = loginUserVO.getUserRole();
        //String 转 List<String>
        return Arrays.asList(userRole.split(","));
    }

}

