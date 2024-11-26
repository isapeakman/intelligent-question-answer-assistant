package com.lightcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.enums.ErrorCode;
import com.lightcs.exception.BusinessException;
import com.lightcs.model.entity.User;
import com.lightcs.service.UserService;
import com.lightcs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author chinese
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-11-25 11:53:32
*/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long userRegister(String userAccount, String userPassword) {

        // 锁定账户
        synchronized (userAccount.intern()){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount",userAccount);
            Long count = userMapper.selectCount(queryWrapper);//获取数目
            if(count>0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户名已存在");
            }
            User user = new User();
            user.setUseraccount(userAccount);
            //todo 加密
            user.setUserpassword(userPassword);
            userMapper.insert(user);  //会将插入的属性自动填充到user
            return user.getId();
        }
    }
}




