package com.lightcs.service;

import com.lightcs.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chinese
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-11-25 11:53:32
*/
public interface UserService extends IService<User> {
    Long userRegister(String userAccount, String userPassword);
}
