package com.lightcs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.enums.ErrorCode;
import com.lightcs.exception.BusinessException;
import com.lightcs.model.entity.User;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.model.vo.UserVO;
import com.lightcs.service.UserService;
import com.lightcs.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lightcs.constant.UserConstants.USER_LOGIN_STATUS;

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
            queryWrapper.eq("user_account",userAccount);
            Long count = userMapper.selectCount(queryWrapper);//获取数目
            if(count>0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户名已存在");
            }
            User user = new User();
            user.setUserAccount(userAccount);
            //todo 加密
            user.setUserPassword(userPassword);
            userMapper.insert(user);  //会将插入的属性自动填充到user
            return user.getId();
        }
    }

    @Override
    public UserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // todo 密码加密
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",userPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名或密码错误");
        }
        request.getSession().setAttribute(USER_LOGIN_STATUS,user);

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATUS);
        if(user==null){
            throw new BusinessException(ErrorCode.UNLOGIN_ERROR);
        }
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return true;
    }

    public LoginUserVO getCurrentLoginUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATUS);
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user,loginUserVO);
        return loginUserVO;
    }
    public User getUser(HttpServletRequest request){
        // todo 获取用户
       request.getSession().getAttribute(USER_LOGIN_STATUS);
       return null;
    }
}




